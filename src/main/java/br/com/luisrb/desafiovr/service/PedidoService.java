package br.com.luisrb.desafiovr.service;

import br.com.luisrb.desafiovr.entity.Cliente;
import br.com.luisrb.desafiovr.entity.ItemPedido;
import br.com.luisrb.desafiovr.entity.Pedido;
import br.com.luisrb.desafiovr.entity.Produto;
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
}
