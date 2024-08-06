package br.com.luisrb.desafiovr.controller;

import br.com.luisrb.desafiovr.entity.Cliente;
import br.com.luisrb.desafiovr.entity.dto.ClienteRequest;
import br.com.luisrb.desafiovr.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastrar(@RequestBody ClienteRequest cliente) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(new Cliente(cliente)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Cliente>> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
