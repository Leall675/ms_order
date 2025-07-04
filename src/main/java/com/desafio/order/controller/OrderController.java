package com.desafio.order.controller;


import com.desafio.order.dto.request.OrderDtoRequest;
import com.desafio.order.dto.response.OrderDtoResponse;
import com.desafio.order.dto.response.ProductDtoResponse;
import com.desafio.order.service.OrderService;
import com.desafio.order.validation.OrderValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderValidation orderValidation;

    @Autowired
    private OrderService orderService;

    @GetMapping("{id}/product")
    public Mono<ProductDtoResponse> buscarProduto(@PathVariable String id) {
        return orderValidation.buscarProduto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderDtoResponse> criarPedido(@Valid @RequestBody OrderDtoRequest orderDto) {
        return orderService.criarPedido(orderDto);
    }

    @GetMapping
    public Flux<OrderDtoResponse> buscarPedidos() {
        return orderService.buscarPedidos();
    }
}
