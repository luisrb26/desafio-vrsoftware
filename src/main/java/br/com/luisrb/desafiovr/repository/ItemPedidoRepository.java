package br.com.luisrb.desafiovr.repository;

import br.com.luisrb.desafiovr.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {


}
