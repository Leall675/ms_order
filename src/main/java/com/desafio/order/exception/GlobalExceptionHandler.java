package com.desafio.order.exception;

import com.desafio.order.dto.error.ErroCampo;
import com.desafio.order.dto.error.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErroResposta> handleProductNotFoundException(ProductNotFoundException ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.NOT_FOUND.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErroResposta> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.NOT_FOUND.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(ProductRetrievalException.class)
    public ResponseEntity<ErroResposta> handleProductRetrievalException(ProductRetrievalException ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErroCampo> erro = ex.getFieldErrors()
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        ErroResposta erroResposta = new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", erro);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroResposta);
    }
}
