package br.com.luisrb.desafiovr.entity.dto;

import java.util.List;

public record PedidoRequest(Long clienteId, List<Long> produtosIds, List<Integer> quantidades) {
}

