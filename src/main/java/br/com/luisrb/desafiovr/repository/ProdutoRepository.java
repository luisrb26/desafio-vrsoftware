package br.com.luisrb.desafiovr.repository;

import br.com.luisrb.desafiovr.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


}
