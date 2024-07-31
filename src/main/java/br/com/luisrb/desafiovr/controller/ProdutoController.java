package br.com.luisrb.desafiovr.controller;

import br.com.luisrb.desafiovr.entity.Produto;
import br.com.luisrb.desafiovr.entity.dto.ProdutoRequest;
import br.com.luisrb.desafiovr.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrar(@RequestBody ProdutoRequest produto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(new Produto(produto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
