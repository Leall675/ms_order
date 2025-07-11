package com.desafio.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDtoUpdate {
    private Long quantity;
    private String operation; // "REDUCE" ou "ADD"
}
