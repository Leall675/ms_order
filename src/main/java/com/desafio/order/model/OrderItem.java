package com.desafio.order.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private String productId;
    private Long quantity;
}
