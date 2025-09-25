package com.reis.JKStore.repository;

import com.reis.JKStore.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findById(Long id);

    Optional<Produto> findByTitulo(String titulo);

    @Query(value = "select * from tb_produtos tp " +
            "where tp.destaque = true " +
            "and tp.active = true", nativeQuery = true)
    Optional<List<Produto>> procurarProdutosDestacados();

}
