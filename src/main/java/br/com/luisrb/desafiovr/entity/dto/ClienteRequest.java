package br.com.luisrb.desafiovr.entity.dto;

import java.math.BigDecimal;

public record ClienteRequest(
        String codigo,
        String nome,
        BigDecimal limiteCompra,
        int diaFechamento
) {
}
