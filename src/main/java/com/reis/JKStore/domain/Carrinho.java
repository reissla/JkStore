package com.reis.JKStore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_carrinhos")
public class Carrinho  extends EntidadeGenerica{

    @OneToOne
    private Usuario usuario;
    @OneToMany
    @JoinTable(
            name = "tb_carrinhos_produtos",
            joinColumns = @JoinColumn(name = "carrinho_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;
}
