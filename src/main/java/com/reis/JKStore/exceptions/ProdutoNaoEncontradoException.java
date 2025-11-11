package com.reis.JKStore.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException{

    public ProdutoNaoEncontradoException(){
        super("Produto não encontrado na base de dados.");
    }

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
