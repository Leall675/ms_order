package com.desafio.order.dto.request;

import com.desafio.order.enuns.OrderStatusEnum;
import com.desafio.order.enuns.PaymentMethod;
import com.desafio.order.model.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoRequest {
    @NotEmpty(message = "A lista de itens não pode ser vazia.")
    @Valid
    private List<OrderItemDto> items;
    @NotNull(message = "Forma de pagamento deve ser informada.")
    private PaymentMethod paymentMethod;
    @NotNull(message = "Status do pedido deve ser informado.")
    private OrderStatusEnum statusOrder;
}
