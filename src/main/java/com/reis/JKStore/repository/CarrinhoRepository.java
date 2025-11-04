package com.reis.JKStore.repository;

import com.reis.JKStore.domain.Carrinho;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    @Query("select c from Carrinho c " +
    "where c.active = true " +
    "and c.usuario.id = :usuarioId")
    Carrinho findCarrinhoByUsuarioId(@Param("usuarioId") Long usuarioId);
}
