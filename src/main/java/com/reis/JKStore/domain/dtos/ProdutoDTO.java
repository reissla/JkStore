package com.reis.JKStore.domain.dtos;

import java.math.BigDecimal;

public record ProdutoDTO(String titulo,
                         String descricao,
                         BigDecimal preco,
                         Boolean disponivel) {
}
