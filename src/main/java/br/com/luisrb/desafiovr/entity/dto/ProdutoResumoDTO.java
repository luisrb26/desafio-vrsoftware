package br.com.luisrb.desafiovr.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoResumoDTO {
    private Long produtoId;
    private String produtoCodigo;
    private String produtoDescricao;
    private Long quantidadeTotal;
    private BigDecimal valorTotal;

    public ProdutoResumoDTO(Long produtoId, String produtoCodigo, String produtoDescricao, Long quantidadeTotal, BigDecimal valorTotal) {
        this.produtoId = produtoId;
        this.produtoCodigo = produtoCodigo;
        this.produtoDescricao = produtoDescricao;
        this.quantidadeTotal = quantidadeTotal;
        this.valorTotal = valorTotal;
    }
}
