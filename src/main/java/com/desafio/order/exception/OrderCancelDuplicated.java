package com.desafio.order.exception;

public class OrderCancelDuplicated extends RuntimeException {
    public OrderCancelDuplicated(String message) {
        super(message);
    }
}
