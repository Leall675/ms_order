package com.desafio.order.controller;


import com.desafio.order.dto.response.ProductDtoResponse;
import com.desafio.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/product/{id}")
    public Mono<ProductDtoResponse> buscarProduto(@PathVariable String id) {
        return orderService.buscarProduto(id);
    }
}
