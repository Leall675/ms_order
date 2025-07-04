package com.desafio.order.mapper;

import com.desafio.order.dto.request.OrderDtoRequest;
import com.desafio.order.dto.request.OrderItemDto;
import com.desafio.order.dto.response.OrderDtoResponse;
import com.desafio.order.dto.response.OrderItemDtoResponse;
import com.desafio.order.model.Order;
import com.desafio.order.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMappers {

    public Order toEntity(OrderDtoRequest dto) {
        Order order = new Order();
        List<OrderItem> items = dto.getItems().stream()
                        .map(itemDto -> {
                            OrderItem item = new OrderItem();
                            item.setProductId(itemDto.getProductId());
                            item.setQuantity(itemDto.getQuantity());
                            return item;
                        })
                        .toList();
        order.setItems(items);
        order.setOrderStatus(dto.getStatusOrder());
        return order;
    }

    public OrderDtoResponse toDto(Order order) {
        OrderDtoResponse response = new OrderDtoResponse();
        response.setId(order.getId());
        response.setPaymentId(order.getPaymentId());
        List<OrderItemDtoResponse> itemDtos = order.getItems().stream()
                .map(item -> {
                    OrderItemDtoResponse itemDtoResponse = new OrderItemDtoResponse();
                    itemDtoResponse.setProductId(item.getProductId());
                    itemDtoResponse.setQuantity(item.getQuantity());
                    return itemDtoResponse;
                })
                .collect(Collectors.toList());
        response.setItems(itemDtos);

        response.setOrderStatus(order.getOrderStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
