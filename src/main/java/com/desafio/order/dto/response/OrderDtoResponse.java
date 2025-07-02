package com.desafio.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoResponse {
    private String id;
    private String paymentId;
    private double totalAmount;
    private LocalDateTime createdAt;
}
