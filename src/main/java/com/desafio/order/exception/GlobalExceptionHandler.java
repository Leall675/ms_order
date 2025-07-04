package com.desafio.order.exception;

import com.desafio.order.dto.error.ErroCampo;
import com.desafio.order.dto.error.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<ErroResposta> handleDuplicateProductException(DuplicateProductException ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resposta);
    }
}
