package com.reis.JKStore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_usuarios")
@Data
@ToString
public class Usuario extends EntidadeGenerica{

    @NotBlank
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(unique = true)
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @Column(name = "roleUsuario")
    @Enumerated(EnumType.STRING)
    private Role role;
}
