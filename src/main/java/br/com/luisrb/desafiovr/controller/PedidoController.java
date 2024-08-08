package br.com.luisrb.desafiovr.controller;

import br.com.luisrb.desafiovr.entity.ItemPedido;
import br.com.luisrb.desafiovr.entity.Pedido;
import br.com.luisrb.desafiovr.entity.dto.PedidoRequest;
import br.com.luisrb.desafiovr.entity.dto.PedidoResponse;
import br.com.luisrb.desafiovr.exception.LimiteCreditoExcedidoException;
import br.com.luisrb.desafiovr.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/buscar-todos-com-itens")
    public ResponseEntity<List<PedidoResponse>> buscarTodos() {
        try {
            return ResponseEntity.ok(pedidoService.findAllPedidosWithItemsAndCliente());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/item-pedido")
    public ResponseEntity<List<ItemPedido>> findAllItemPedidos() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.findAllItemPedidos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody PedidoRequest pedidoRequest) {
        try {
            Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoRequest);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (LimiteCreditoExcedidoException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
