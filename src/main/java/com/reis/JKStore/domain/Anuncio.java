package com.reis.JKStore.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_anuncios")
@Data
public class Anuncio extends EntidadeGenerica{

    @Column(name = "produto")
    private Produto produto;

    @Column(name = "dataInicioAnuncio")
    private Date dataInicio;

    @Column(name = "dataFimAnuncio")
    private Date dataFim;

    @Column(name = "destaque")
    private Boolean destaque;

    @Column(name = "categoriaProduto")
    @Enumerated(EnumType.STRING)
    private Categoria categoriaProduto;
}
