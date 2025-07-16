package com.desafio.order.service;

import com.desafio.order.dto.request.StockDtoUpdateRequest;
import com.desafio.order.dto.response.ProductDtoResponse;
import com.desafio.order.exception.ProductNotFoundException;
import com.desafio.order.exception.ProductRetrievalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ProductNotFoundException("Produto não localizado na base de dados."));
                    }
                    return Mono.error(new ProductRetrievalException("Erro ao buscar produto: " + clientResponse.statusCode()));
                })
                .bodyToMono(ProductDtoResponse.class);
    }


    public Mono<Void> updateProduct(String productId, long quantity, String operation) {
        StockDtoUpdateRequest dtoUpdate = new StockDtoUpdateRequest(quantity, operation);
        return webClient.patch()
                .uri("/v1/products/{id}/stock", productId)
                .bodyValue(dtoUpdate)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
