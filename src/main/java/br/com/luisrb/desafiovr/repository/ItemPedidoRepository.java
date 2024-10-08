package br.com.luisrb.desafiovr.repository;

import br.com.luisrb.desafiovr.entity.ItemPedido;
import br.com.luisrb.desafiovr.entity.dto.ProdutoResumoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("SELECT  ip  from ItemPedido ip " +
            "WHERE ip.pedido.id = :idPedido")
    List<ItemPedido> findAllByPedidoId(Long idPedido);

    void deleteAllByPedidoId(Long pedidoId);

    ItemPedido findByPedidoIdAndProdutoId(Long pedidoId, Long produtoId);
}
