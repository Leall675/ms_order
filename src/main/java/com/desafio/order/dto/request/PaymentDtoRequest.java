package com.desafio.order.dto.request;

import com.desafio.order.enuns.PaymentMethod;
import com.desafio.order.enuns.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDtoRequest {
    @NotNull(message = "O valor não pode ser nulo.")
    @Positive(message = "O valor do pagamento deve ser maior que zero.")
    private Double amount;
    @NotNull(message = "O método de pagamento não pode ser nulo.")
    private PaymentMethod paymentMethod;
    @NotNull(message = "O status do pagamento não pode ser nulo.")
    private PaymentStatus paymentStatus;
}
