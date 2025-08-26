package com.reis.JKStore.controller;


import com.reis.JKStore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @PostMapping("/cadastrarProduto")
    public ResponseEntity<?> cadastrarProduto(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/removerProduto")
    public ResponseEntity<?> removerProduto(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PutMapping("/editarProduto")
    public ResponseEntity<?> editarProduto(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
