package br.com.luisrb.desafiovr.entity.dto;

import br.com.luisrb.desafiovr.entity.Cliente;
import br.com.luisrb.desafiovr.entity.ItemPedido;
import br.com.luisrb.desafiovr.entity.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PedidoResponse {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataPedido;
    private String situacao;
    private Cliente cliente;
    private List<ItemPedido> itens;

    // Construtor
    public PedidoResponse(Pedido pedido, List<ItemPedido> itens) {
        this.id = pedido.getId();
        this.dataPedido = pedido.getDataPedido();
        this.situacao = pedido.getSituacao();
        this.cliente = pedido.getCliente();
        this.itens = itens;
    }
}
