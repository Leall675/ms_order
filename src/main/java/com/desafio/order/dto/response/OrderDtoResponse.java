package com.desafio.order.dto.response;

import com.desafio.order.enuns.OrderStatusEnum;
import com.desafio.order.enuns.PaymentMethod;
import com.desafio.order.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoResponse {
    private String id;
    private String paymentId;
    private List<OrderItemDtoResponse> items;
    private String totalAmount;
    private PaymentMethod paymentMethod;
    private OrderStatusEnum orderStatus;
    private LocalDateTime createdAt;
}
