package br.com.luisrb.desafiovr.controller;

import br.com.luisrb.desafiovr.entity.Pedido;
import br.com.luisrb.desafiovr.entity.dto.PedidoRequest;
import br.com.luisrb.desafiovr.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Pedido> adicionarPedido(@RequestBody PedidoRequest pedidoRequest) {
        try {
            Pedido pedido = pedidoService.adicionarPedido(
                    pedidoRequest.clienteId(),
                    pedidoRequest.produtosIds(),
                    pedidoRequest.quantidades()
            );
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
