package com.desafio.order.service;

import com.desafio.order.dto.request.OrderDtoRequest;
import com.desafio.order.dto.request.OrderItemDto;
import com.desafio.order.dto.response.OrderDtoResponse;
import com.desafio.order.dto.response.PaymentDtoResponse;
import com.desafio.order.dto.response.ProductDtoResponse;
import com.desafio.order.enuns.OrderStatusEnum;
import com.desafio.order.enuns.PaymentMethod;
import com.desafio.order.enuns.PaymentStatus;
import com.desafio.order.exception.OrderCancelDuplicated;
import com.desafio.order.exception.OrderNotFoundException;
import com.desafio.order.mapper.OrderMappers;
import com.desafio.order.model.Order;
import com.desafio.order.model.OrderItem;
import com.desafio.order.repository.OrderRepository;
import com.desafio.order.validation.OrderValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderValidation orderValidation;

    @Mock
    private OrderMappers orderMappers;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductsIntegrationService productsIntegrationService;

    @Mock
    private PaymentsIntegrationService paymentsIntegrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Dado um pedido válido, quando criar o pedido, então retorna o objeto OrderDtoResponse correspondente")
    void deveCriarPedidoComSucesso() {
        OrderDtoRequest dtoRequest = new OrderDtoRequest();
        dtoRequest.setPaymentMethod(PaymentMethod.CARTAO);

        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setProductId("prod-123");
        itemDto.setQuantity(2L);
        dtoRequest.setItems(List.of(itemDto));

        ProductDtoResponse productResponse = new ProductDtoResponse();
        productResponse.setId("prod-123");
        productResponse.setPrice(10.0);

        Order order = new Order();
        OrderItem item = new OrderItem();
        item.setProductId("prod-123");
        item.setQuantity(2L);
        order.setItems(List.of(item));

        OrderDtoResponse expectedDtoResponse = new OrderDtoResponse();
        expectedDtoResponse.setId("order-1");

        PaymentDtoResponse paymentResponse = new PaymentDtoResponse();
        paymentResponse.setId("payment-1");

        Mockito.when(orderValidation.validarDuplicidadeDeProdutos(anyList()))
                .thenReturn(Mono.empty());

        Mockito.when(orderValidation.validarProduto("prod-123", 2))
                .thenReturn(Mono.just(productResponse));

        Mockito.when(orderMappers.toEntity(dtoRequest))
                .thenReturn(order);

        Mockito.when(paymentsIntegrationService.createdPayment(20.0, PaymentMethod.CARTAO, PaymentStatus.PENDENTE))
                .thenReturn(Mono.just(paymentResponse));

        Mockito.when(productsIntegrationService.updateProduct("prod-123", 2, "REDUCE"))
                .thenReturn(Mono.empty());

        Mockito.when(orderRepository.save(order))
                .thenReturn(order);

        Mockito.when(orderMappers.toDto(order))
                .thenReturn(expectedDtoResponse);

        OrderDtoResponse result = orderService.criarPedido(dtoRequest).block();

        assertNotNull(result);
        assertEquals("order-1", result.getId());
        assertEquals("payment-1", order.getPaymentId());
    }

    @Test
    @DisplayName("Dado um pedido existente, quando cancelar o pedido, então deve atualizar o status para CANCELADO")
    void testdeveCancelarPedidoComSucesso() {
        String orderId = "123";
        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(OrderStatusEnum.PROCESSANDO);
        order.setItems(new ArrayList<>());

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId("produto-teste-123");
        orderItem.setQuantity(10L);
        orderItem.setOrder(order);
        order.getItems().add(orderItem);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productsIntegrationService.updateProduct(anyString(), anyLong(), eq("ADD")))
                .thenReturn(Mono.empty());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Mono<Void> result = orderService.cancelarPedido(orderId);

        result.block();
        assertEquals(OrderStatusEnum.CANCELADO, order.getOrderStatus());
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("Dado um pedido que não existe, quando cancelar o pedido, então lança OrderNotFoundException")
    void testCancelarPedido_OrderNotFoundException() {
        String orderId = "invalid-id";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.cancelarPedido(orderId).block());

    }

    @Test
    @DisplayName("Dado um pedido já cancelado, quando cancelar o pedido, então lança OrderCancelDuplicated")
    void testCancelarPedido_OrderAlreadyCanceled(){
        String orderId = "duplicated-id";
        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(OrderStatusEnum.CANCELADO);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(OrderCancelDuplicated.class, () -> orderService.cancelarPedido(orderId).block());
    }

    @Test
    @DisplayName("Dado pedidos existentes, quando buscar pedidos, então retorna o objeto OrderDtoResponse correspondente")
    void testBuscarPedidos_Success() {
        List<Order> list = new ArrayList<>();

        Order order1 = new Order();
        Order order2 = new Order();
        order1.setId("order-1");
        order2.setId("order-2");

        list.add(order1);
        list.add(order2);

        OrderDtoResponse orderDtoResponse1 = new OrderDtoResponse();
        OrderDtoResponse orderDtoResponse2 = new OrderDtoResponse();

        orderDtoResponse1.setId(order1.getId());
        orderDtoResponse2.setId(order2.getId());

        when(orderRepository.findAll()).thenReturn(list);
        when(orderMappers.toDto(order1)).thenReturn(orderDtoResponse1);
        when(orderMappers.toDto(order2)).thenReturn(orderDtoResponse2);


        Flux<OrderDtoResponse> responseFlux = orderService.buscarPedidos();

        List<OrderDtoResponse> orderDtoResponses = responseFlux.collectList().block();
        assertEquals(2, orderDtoResponses.size());
        assertEquals(orderDtoResponse1.getId(), orderDtoResponses.get(0).getId());
        assertEquals(orderDtoResponse2.getId(), orderDtoResponses.get(1).getId());

    }

    @Test
    @DisplayName("Quando não existem pedidos, então retorna uma lista vazia de OrderDtoResponse")
    void testBuscarPedidos_Empty() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        Flux<OrderDtoResponse> responseFlux = orderService.buscarPedidos();

        List<OrderDtoResponse> orderDtoResponses = responseFlux.collectList().block();

        assertNotNull(orderDtoResponses);
        assertEquals(0, orderDtoResponses.size());
    }
}

