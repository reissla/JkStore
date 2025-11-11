package com.reis.JKStore.exceptions;

public class IdNullException extends RuntimeException{

    public IdNullException(){
        super("Id passado não pode ser nullo.");
    }

    public IdNullException(String message) {
        super(message);
    }
}
