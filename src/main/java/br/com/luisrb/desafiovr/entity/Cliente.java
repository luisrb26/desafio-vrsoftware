package br.com.luisrb.desafiovr.entity;

import br.com.luisrb.desafiovr.entity.dto.ClienteRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente", schema = "public")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String nome;

    @Column(name = "limite_compra")
    private BigDecimal limiteCompra;

    @Column(name = "dia_fechamento")
    private int diaFechamento;

    public Cliente(ClienteRequest clienteRequest) {
        nome = clienteRequest.nome();
        codigo = clienteRequest.codigo();
        limiteCompra = clienteRequest.limiteCompra();
        diaFechamento = clienteRequest.diaFechamento();
    }

}