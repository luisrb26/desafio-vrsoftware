package br.com.luisrb.desafiovr.entity;

import br.com.luisrb.desafiovr.entity.dto.ProdutoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto", schema = "public")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String descricao;

    private BigDecimal preco;

    public Produto(ProdutoRequest produto) {
        codigo = produto.codigo();
        descricao = produto.descricao();
        preco = produto.preco();
    }
}
