package com.desafio.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDtoRequest {
    @NotBlank(message = "ID do produto não pode ser nulo ou vazio.")
    private String productId;
    @Positive(message = "Quantidade do produto não pode ser 0 ou negativo.")
    private Long quantity;

}
