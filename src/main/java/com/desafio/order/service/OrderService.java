package com.desafio.order.service;

import com.desafio.order.dto.response.ProductDtoResponse;
import com.desafio.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductsIntegrationService productsIntegrationService;

    public Mono<ProductDtoResponse> buscarProduto(String productId) {
        return productsIntegrationService.getProductById(productId)
                .doOnNext(product -> {
                    System.out.println("Produto encontrado: " + product.getName());
                });
    }

}
