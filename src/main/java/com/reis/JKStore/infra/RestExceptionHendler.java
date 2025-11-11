package com.reis.JKStore.infra;

import com.reis.JKStore.exceptions.IdNullException;
import com.reis.JKStore.exceptions.ProdutoIndisponivelException;
import com.reis.JKStore.exceptions.ProdutoNaoEncontradoException;
import com.reis.JKStore.exceptions.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHendler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdNullException.class)
    private ResponseEntity<RestErrorMessage> idNullHandler(IdNullException exception) {
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatResponse);
    }

    @ExceptionHandler(ProdutoIndisponivelException.class)
    private ResponseEntity<RestErrorMessage> produtoIndisponivelExceptionHendler(ProdutoIndisponivelException exception) {
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(treatResponse);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    private ResponseEntity<RestErrorMessage> produtoNaoEncontradoExceptionHendler(ProdutoNaoEncontradoException exception) {
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatResponse);
    }

}
