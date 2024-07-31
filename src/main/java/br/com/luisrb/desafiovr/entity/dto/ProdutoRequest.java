package br.com.luisrb.desafiovr.entity.dto;

import java.math.BigDecimal;

public record ProdutoRequest(
        String codigo,
        String descricao,
        BigDecimal preco
) {
}
