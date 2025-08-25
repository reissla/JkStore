package com.reis.JKStore.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntidadeGenerica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean active = true;
    @CreatedDate
    @Timestamp
    @Column(name = "data_criacao")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date dataCriacao;
    @LastModifiedDate
    @Timestamp
    @Column(name = "data_edicao")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date dataEdicao;

}
