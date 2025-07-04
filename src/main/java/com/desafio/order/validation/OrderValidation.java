package com.desafio.order.validation;

import com.desafio.order.dto.request.OrderItemDto;
import com.desafio.order.dto.response.ProductDtoResponse;
import com.desafio.order.exception.DuplicateProductException;
import com.desafio.order.exception.InsufficientStockException;
import com.desafio.order.service.ProductsIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class OrderValidation {

    @Autowired
    private ProductsIntegrationService productsIntegrationService;

    public Mono<ProductDtoResponse> buscarProduto(String productId) {
        return productsIntegrationService.getProductById(productId);
    }

    public Mono<ProductDtoResponse> validarProduto(String productId, long quantity) {
        return buscarProduto(productId)
                .flatMap(produto -> {
                    if (produto.getQuantity() < quantity ) {
                        return Mono.error(new InsufficientStockException("Produto sem estoque suficiente"));
                    }
                    ProductDtoResponse productDtoResponse = new ProductDtoResponse(
                            produto.getId(),
                            produto.getName(),
                            produto.getPrice(),
                            produto.getQuantity()
                    );
                    return Mono.just(productDtoResponse);
                });
    }

    public Mono<Void> validarDuplicidadeDeProdutos(List<OrderItemDto> items) {
        Set<String> uniqueProductIds = new HashSet<>();
        for (OrderItemDto item : items) {
            if (!uniqueProductIds.add(item.getProductId())) {
                return Mono.error(new DuplicateProductException("Duplicate productId detected in order items."));
            }
        }
        return Mono.empty();
    }

}
