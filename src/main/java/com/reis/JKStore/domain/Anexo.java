package com.reis.JKStore.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "anexos")
@ToString
@EqualsAndHashCode(callSuper = false)
public class Anexo extends EntidadeGenerica {

    private String nome;
    @Column(name = "nome_exibicao")
    private String nomeExibicao;
    @Column(name = "ordem_insercao")
    private Integer ordemInsercao;
    private String url;
    private String base64;
}
