package com.reis.JKStore.service;

import com.reis.JKStore.domain.Carrinho;
import com.reis.JKStore.domain.Produto;
import com.reis.JKStore.domain.Usuario;
import com.reis.JKStore.domain.dtos.ProdutoDTO;
import com.reis.JKStore.repository.CarrinhoRepository;
import com.reis.JKStore.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CarrinhoRepository carrinhoRepository;
    @Autowired
    UsuarioService usuarioService;

    public void cadastrarProduto(Produto produto) {
        Produto pr = procurarPorTitulo(produto.getTitulo());
        if(pr != null){
            throw new RuntimeException("Produto já existe na base de dados.");
        }
        produtoRepository.save(produto);
    }

    @Transactional
    public void removerProdutoPorId(Long id) {
        if (id == null) {
            throw new RuntimeException("Id passado não pode ser Null");
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

    public List<Produto> listarProdutosEmDetaque(){
        return produtoRepository.procurarProdutosDestacados().
                orElseThrow(() -> new RuntimeException("Erro ao procurar produtos destacados."));
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

    @Transactional
    public void atualizarStatusDestaqueProduto(Long produtoId, Boolean destaque){
        if(produtoId == null || destaque == null){
            throw new RuntimeException("nenhum valor passado deve ser null");
        }

        Produto produto = procurarPorId(produtoId);

        produto.setDestaque(destaque);
    }

    @Transactional
    public void adicionarProdutoAoCarrinho(Long produtoId) {
        if (produtoId == null) {
            throw new IllegalArgumentException("O ID do produto não pode ser nulo");
        }

        Produto produto = procurarPorId(produtoId);
        if (!produtoDisponivel(produto.getId())) {
            throw new IllegalStateException("Produto indisponível no momento");
        }

        Usuario usuario = usuarioService.usuarioLogado();
        Carrinho carrinho = carrinhoRepository.findCarrinhoByUsuarioId(usuario.getId());

        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
            carrinho.setProdutos(new ArrayList<>());
        }

        carrinho.getProdutos().add(produto);
        carrinhoRepository.save(carrinho);
    }

    public List<Produto> listarProdutosNoCarrinho(){
        Long usuarioId = usuarioService.usuarioLogado().getId();
        return produtoRepository.listarProdutosNoCarrinhoPorUsuarioId(usuarioId);
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto procurarPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }

    public Produto procurarPorTitulo(String titulo){
        return produtoRepository.findByTitulo(titulo).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }
}
