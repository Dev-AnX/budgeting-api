package com.carteira.api;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private String categoria;
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Construtores
    public Transacao() {}
    public Transacao(String descricao, BigDecimal valor, String categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String d) { this.descricao = d; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String c) { this.categoria = c; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
