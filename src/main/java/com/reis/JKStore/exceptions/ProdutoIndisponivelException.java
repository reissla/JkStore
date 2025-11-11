package com.reis.JKStore.exceptions;

public class ProdutoIndisponivelException extends RuntimeException{

    public ProdutoIndisponivelException(){
        super("O produto está indisponível no momento.");
    }

    public ProdutoIndisponivelException(String message) {
        super(message);
    }
}
