package com.desafio.order.service;

import com.desafio.order.dto.request.StockDtoUpdate;
import com.desafio.order.dto.response.ProductDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductsIntegrationService {

    private final WebClient webClient;

    @Autowired
    public ProductsIntegrationService(WebClient webClientProducts) {
        this.webClient = webClientProducts;
    }

    public Mono<ProductDtoResponse> getProductById(String productId) {
        return webClient.get()
                .uri("/v1/products/{id}", productId)
                .retrieve()
                .bodyToMono(ProductDtoResponse.class)
                .onErrorResume(e -> {
                    return Mono.empty();
                });
    }

    public Mono<Void> updateProduct(String productId, long quantity) {
        StockDtoUpdate dtoUpdate = new StockDtoUpdate(quantity, "REDUCE");
        return webClient.patch()
                .uri("/v1/products/{id}/stock", productId)
                .bodyValue(dtoUpdate)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
