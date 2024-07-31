package br.com.luisrb.desafiovr.service;

import br.com.luisrb.desafiovr.entity.Produto;
import br.com.luisrb.desafiovr.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }
}
