package br.com.luisrb.desafiovr.service;

import br.com.luisrb.desafiovr.entity.Cliente;
import br.com.luisrb.desafiovr.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
