package com.reis.JKStore.exceptions;

public class ValorNullException extends RuntimeException{

    public ValorNullException(){
        super("O valor passado não pode ser null");
    }

    public ValorNullException(String message) {
        super(message);
    }
}
