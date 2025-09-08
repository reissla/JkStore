package com.reis.JKStore.service;

import com.reis.JKStore.domain.Produto;
import com.reis.JKStore.domain.dtos.ProdutoDTO;
import com.reis.JKStore.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioService usuarioService;

    public void cadastrarProduto(Produto produto) {
        if(procurarPorTitulo(produto.getTitulo()) != null){
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
    public void editarProduto(Long id,ProdutoDTO produto) {
        if (produto == null) {
            throw new RuntimeException();
        }

        Produto produtoAtual = procurarPorId(id);
        BeanUtils.copyProperties(produto, produtoAtual);
        produtoAtual.setDataEdicao(LocalDate.now());
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

    public Produto procurarPorTitulo(String titulo){
        return produtoRepository.findByTitulo(titulo).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }
}
