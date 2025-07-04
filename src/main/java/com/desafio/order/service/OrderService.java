package com.desafio.order.service;

import com.desafio.order.dto.request.OrderDtoRequest;
import com.desafio.order.dto.response.OrderDtoResponse;
import com.desafio.order.mapper.OrderMappers;
import com.desafio.order.model.Order;
import com.desafio.order.model.OrderItem;
import com.desafio.order.repository.OrderRepository;
import com.desafio.order.validation.OrderValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    @Autowired
    private OrderValidation orderValidation;

    @Autowired
    private OrderMappers orderMappers;

    @Autowired
    private OrderRepository orderRepository;

    public Mono<OrderDtoResponse> criarPedido(OrderDtoRequest orderDto) {
        return orderValidation.validarDuplicidadeDeProdutos(orderDto.getItems())
                .then(Flux.fromIterable(orderDto.getItems())
                        .flatMap(item -> orderValidation.validarProduto(item.getProductId(), item.getQuantity()))
                        .collectList()
                        .flatMap(produtos -> Mono.fromCallable(() -> {
                            Order order = orderMappers.toEntity(orderDto);
                            order.getItems().forEach(item -> item.setOrder(order));

                            double totalAmount = produtos.stream()
                                    .mapToDouble(produtoDto -> {
                                        OrderItem item = order.getItems().stream()
                                                .filter(orderItem -> orderItem.getProductId().equals(produtoDto.getId()))
                                                .findFirst().orElseThrow();
                                        return item.getQuantity() * produtoDto.getPrice();
                                    }).sum();

                            String totalAmountFormatted = String.format("%.2f", totalAmount);
                            order.setTotalAmount(totalAmountFormatted);

                            orderRepository.save(order);
                            return order;
                        }))
                )
                .map(orderMappers::toDto); // Make sure toDto is working on a single Order
    }

    public Flux<OrderDtoResponse> buscarPedidos() {
        return Flux.fromIterable(orderRepository.findAll())
                .map(orderMappers::toDto);
    }

}
