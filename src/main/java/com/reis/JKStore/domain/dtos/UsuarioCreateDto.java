package com.reis.JKStore.domain.dtos;

public record UsuarioCreateDto(String nome,
                               String login,
                               String email,
                               String senha) {
}
