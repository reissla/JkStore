package com.reis.JKStore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_produtos")
@Data
@ToString
public class Produto extends EntidadeGenerica{

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Boolean disponivel;
}
