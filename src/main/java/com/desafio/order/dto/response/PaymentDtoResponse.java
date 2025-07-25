package com.desafio.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDtoResponse {
    private String id;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime createdAt;

    public PaymentDtoResponse(String paymentId) {
    }
}
