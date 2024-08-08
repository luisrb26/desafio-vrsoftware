package br.com.luisrb.desafiovr.repository;

import br.com.luisrb.desafiovr.entity.Cliente;
import br.com.luisrb.desafiovr.entity.ItemPedido;
import br.com.luisrb.desafiovr.entity.Pedido;
import br.com.luisrb.desafiovr.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ItemPedidoRepositoryTest {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Pedido pedido;

    @BeforeEach
    public void setUp() {
        Cliente cliente = new Cliente(null, "123", "Cliente Teste", new BigDecimal("1000.00"), 10);
        clienteRepository.save(cliente);

        pedido = new Pedido(null, cliente, LocalDateTime.now(), "ABERTO");
        pedidoRepository.save(pedido);

        Produto produto = new Produto(null, "001", "Produto Teste", new BigDecimal("10.00"));
        produtoRepository.save(produto);

        ItemPedido itemPedido = new ItemPedido(null, pedido, produto, 2, new BigDecimal("10.00"));
        itemPedidoRepository.save(itemPedido);
    }

    @Test
    public void testFindAllByPedidoId() {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAllByPedidoId(pedido.getId());
        assertThat(itemPedidos).isNotEmpty();
        assertThat(itemPedidos.get(0).getPedido().getId()).isEqualTo(pedido.getId());
    }
}
