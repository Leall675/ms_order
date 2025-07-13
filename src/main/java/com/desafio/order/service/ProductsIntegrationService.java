package com.desafio.order.service;

import com.desafio.order.dto.request.StockDtoUpdateRequest;
import com.desafio.order.dto.response.ProductDtoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductsIntegrationService {

    private final WebClient webClient;

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
        StockDtoUpdateRequest dtoUpdate = new StockDtoUpdateRequest(quantity, "REDUCE");
        return webClient.patch()
                .uri("/v1/products/{id}/stock", productId)
                .bodyValue(dtoUpdate)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
