package com.reis.JKStore.service;

import com.reis.JKStore.domain.Produto;
import com.reis.JKStore.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioService usuarioService;

    public void cadastrarProduto(Produto produto) {
        if (produtoDisponivel(produto.getId())) {
            throw new RuntimeException();
        }

        produtoRepository.save(produto);
    }

    @Transactional
    public void removerProdutoPorId(Long id) {
        if (id == null) {
            throw new RuntimeException();
        }

        Produto produto = procurarPorId(id);
        produto.setActive(false);
        produtoRepository.save(produto);
    }

    @Transactional
    public void editarProduto(Produto produto) {
        if (produto == null) {
            throw new RuntimeException();
        }

        Produto produtoAtual = procurarPorId(produto.getId());
        BeanUtils.copyProperties(produto, produtoAtual);
        produtoRepository.save(produtoAtual);
    }

    public boolean produtoDisponivel(Long produtoId){
        if(produtoId == null){
            throw new RuntimeException();
        }

        Produto produto = procurarPorId(produtoId);
        if(produto.getDisponivel()){
            return true;
        }
        return false;
    }

    public Produto procurarPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }

}
