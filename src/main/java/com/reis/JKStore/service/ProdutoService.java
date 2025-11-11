package com.reis.JKStore.service;

import com.reis.JKStore.domain.Carrinho;
import com.reis.JKStore.domain.Produto;
import com.reis.JKStore.domain.Usuario;
import com.reis.JKStore.domain.dtos.ProdutoDTO;
import com.reis.JKStore.exceptions.IdNullException;
import com.reis.JKStore.exceptions.ProdutoIndisponivelException;
import com.reis.JKStore.exceptions.ProdutoNaoEncontradoException;
import com.reis.JKStore.exceptions.ValorNullException;
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
            throw new ProdutoIndisponivelException();
        }
        produtoRepository.save(produto);
    }

    @Transactional
    public void removerProdutoPorId(Long id) {
        verificarIdNull(id);

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
                orElseThrow(ProdutoNaoEncontradoException::new);
    }

    @Transactional
    public void atualizarStatusDestaqueProduto(Long produtoId, Boolean destaque){
        if(produtoId == null || destaque == null){
            throw new ValorNullException();
        }
        Produto produto = procurarPorId(produtoId);

        produto.setDestaque(destaque);
    }

    @Transactional
    public void adicionarProdutoAoCarrinho(Long produtoId) {
        verificarIdNull(produtoId);
        Produto produto = procurarPorId(produtoId);
        produtoDisponivel(produtoId);

        Usuario usuario = usuarioService.usuarioLogado();
        Carrinho carrinho = carrinhoRepository.findCarrinhoByUsuarioId(usuario.getId());

        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
            carrinho.setProdutos(new ArrayList<>());
        } else {
            carrinho.getProdutos().forEach( p -> {
                if(p.getId().equals(produtoId)) throw new ProdutoIndisponivelException();
            });
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
        return produtoRepository.findById(id).orElseThrow(ProdutoNaoEncontradoException::new);
    }

    public Produto procurarPorTitulo(String titulo){
        return produtoRepository.findByTitulo(titulo).orElseThrow(ProdutoNaoEncontradoException::new);
    }

    public void produtoDisponivel(Long produtoId){
        if(produtoId == null){
            throw new IdNullException();
        }

        Produto produto = procurarPorId(produtoId);
        if(!produto.getDisponivel()){
            throw new ProdutoIndisponivelException();
        }
    }

    public void verificarIdNull(Long id){
        if(id == null){
            throw new IdNullException();
        }
    }
}
