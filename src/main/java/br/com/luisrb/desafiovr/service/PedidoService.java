package br.com.luisrb.desafiovr.service;

import br.com.luisrb.desafiovr.entity.Cliente;
import br.com.luisrb.desafiovr.entity.ItemPedido;
import br.com.luisrb.desafiovr.entity.Pedido;
import br.com.luisrb.desafiovr.entity.Produto;
import br.com.luisrb.desafiovr.entity.dto.PedidoRequest;
import br.com.luisrb.desafiovr.entity.dto.PedidoResponse;
import br.com.luisrb.desafiovr.exception.LimiteCreditoExcedidoException;
import br.com.luisrb.desafiovr.repository.ClienteRepository;
import br.com.luisrb.desafiovr.repository.ItemPedidoRepository;
import br.com.luisrb.desafiovr.repository.PedidoRepository;
import br.com.luisrb.desafiovr.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Transactional
    public Pedido adicionarPedido(Long clienteId, List<Long> produtosIds, List<Integer> quantidades) throws Exception {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new Exception("Cliente não encontrado"));


        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setSituacao("ativo");
        pedidoRepository.save(pedido);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (int i = 0; i < produtosIds.size(); i++) {
            Produto produto = produtoRepository.findById(produtosIds.get(i))
                    .orElseThrow(() -> new Exception("Produto não encontrado"));
            int quantidade = quantidades.get(i);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(quantidade);
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedidoRepository.save(itemPedido);

            totalPedido = totalPedido.add(produto.getPreco().multiply(new BigDecimal(quantidade)));
        }

        BigDecimal limiteDisponivel = cliente.getLimiteCompra().subtract(totalPedido);
        if (limiteDisponivel.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Limite de crédito excedido. Limite disponível: " + limiteDisponivel + ", Dia de fechamento: " + cliente.getDiaFechamento());
        }

        return pedido;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public List<PedidoResponse> findAllPedidosWithItemsAndCliente() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponse> pedidosWithItems = new ArrayList<>();

        pedidos.forEach(pedido -> {
            List<ItemPedido> itensDoPedido = itemPedidoRepository.findAllByPedidoId(pedido.getId());

            itensDoPedido.forEach(item -> item.setPedido(null));

            PedidoResponse pedidoWithItem = new PedidoResponse(pedido, itensDoPedido);
            pedidosWithItems.add(pedidoWithItem);
        });

        return pedidosWithItems;
    }

    public List<ItemPedido> findAllItemPedidos() {
        return itemPedidoRepository.findAll();
    }

    @Transactional
    public void deletarPedido(Long pedidoId) throws Exception {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new Exception("Pedido não encontrado"));

        itemPedidoRepository.deleteAllByPedidoId(pedidoId);
        pedidoRepository.delete(pedido);
    }

    @Transactional
    public Pedido atualizarPedido(Long pedidoId, PedidoRequest pedidoRequest) throws Exception {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new Exception("Pedido não encontrado"));

        Cliente cliente = clienteRepository.findById(pedidoRequest.clienteId())
                .orElseThrow(() -> new Exception("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setSituacao("ativo");

        itemPedidoRepository.deleteAllByPedidoId(pedidoId);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (int i = 0; i < pedidoRequest.produtosIds().size(); i++) {
            Produto produto = produtoRepository.findById(pedidoRequest.produtosIds().get(i))
                    .orElseThrow(() -> new Exception("Produto não encontrado"));
            int quantidade = pedidoRequest.quantidades().get(i);

            ItemPedido itemPedidoExistente = itemPedidoRepository.findByPedidoIdAndProdutoId(pedido.getId(), produto.getId());
            if (itemPedidoExistente != null) {
                itemPedidoExistente.setQuantidade(itemPedidoExistente.getQuantidade() + quantidade);
                itemPedidoRepository.save(itemPedidoExistente);
            } else {
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setPedido(pedido);
                itemPedido.setProduto(produto);
                itemPedido.setQuantidade(quantidade);
                itemPedido.setPrecoUnitario(produto.getPreco());
                itemPedidoRepository.save(itemPedido);
            }
            totalPedido = totalPedido.add(produto.getPreco().multiply(new BigDecimal(quantidade)));
        }

        BigDecimal limiteDisponivel = cliente.getLimiteCompra().subtract(totalPedido);
        if (limiteDisponivel.compareTo(BigDecimal.ZERO) < 0) {
            throw new LimiteCreditoExcedidoException("Limite de crédito excedido. Limite disponível: " + limiteDisponivel + ", Dia de fechamento: " + cliente.getDiaFechamento());
        }

        pedidoRepository.save(pedido);
        return pedido;
    }
}
