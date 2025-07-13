package com.desafio.order.exception;

import com.desafio.order.dto.error.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<ErroResposta> handleDuplicateProductException(DuplicateProductException ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.BAD_REQUEST.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErroResposta> handleInsufficientStockException(InsufficientStockException ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.BAD_REQUEST.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro de validação: verifique os tipos de dados enviados.", List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericException(Exception ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resposta.setMessage("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}
