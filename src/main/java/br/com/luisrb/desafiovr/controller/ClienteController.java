package br.com.luisrb.desafiovr.controller;

import br.com.luisrb.desafiovr.entity.Cliente;
import br.com.luisrb.desafiovr.entity.dto.ClienteRequest;
import br.com.luisrb.desafiovr.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
