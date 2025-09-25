package com.reis.JKStore.controller;


import com.reis.JKStore.domain.Produto;
import com.reis.JKStore.domain.dtos.ProdutoDTO;
import com.reis.JKStore.repository.ProdutoRepository;
import com.reis.JKStore.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoService produtoService;

    @PostMapping("/cadastrarProduto")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<?> cadastrarProduto(@Valid  @RequestBody Produto produto){
        produtoService.cadastrarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/removerProdutoPorId")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<?> removerProduto(@RequestParam Long id){
        produtoService.removerProdutoPorId(id);
        return ResponseEntity.status(HttpStatus.LOCKED).build();
    }

    @PutMapping("/editarProduto")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<?> editarProduto(@RequestParam Long id,@RequestBody ProdutoDTO produto){
        produtoService.editarProduto(id,produto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/verificarDisponibilidadeProduto")
    public ResponseEntity<?> verificarDisponibilidade(@RequestParam Long produtoId){
        produtoService.produtoDisponivel(produtoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/listarProdutos")
    public ResponseEntity<List<Produto>> listarProdutos(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarProdutos());
    }

    @GetMapping("/listarProdutosEmDestaque")
    public ResponseEntity<List<Produto>> listarProdutosEmDestaque(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarProdutosEmDetaque());
    }

    @PostMapping("/atualizarStatusDestaqueProduto")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<?> atualizarStatusDestaqueProduto(@RequestParam Long produtoId,
                                             @RequestParam Boolean destaque){
        produtoService.atualizarStatusDestaqueProduto(produtoId, destaque);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
