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
public class OrderItemDto {

    @NotBlank(message = "O ID do produto não pode ser nulo ou vazio.")
    private String productId;

    @Positive(message = "A quantidade deve ser maior que 0.")
    private Long quantity;

}
