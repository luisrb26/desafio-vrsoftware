package br.com.luisrb.desafiovr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_pedido", schema = "public")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto", nullable = false)
    private Produto produto;

    private int quantidade;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;
}
