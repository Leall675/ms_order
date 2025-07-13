package com.desafio.order.service;

import com.desafio.order.dto.request.PaymentDtoRequest;
import com.desafio.order.dto.response.PaymentDtoResponse;
import com.desafio.order.dto.response.ProductDtoResponse;
import com.desafio.order.enuns.PaymentMethod;
import com.desafio.order.enuns.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentsIntegrationService {

    private final WebClient webClient;

    public PaymentsIntegrationService(WebClient webClientPayments) {
        this.webClient = webClientPayments;
    }

    public Mono<PaymentDtoResponse> createdPayment(Double amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        PaymentDtoRequest dtoPayment = new PaymentDtoRequest(amount, paymentMethod, paymentStatus);

        return webClient.post()
                .uri("/v1/payments")
                .bodyValue(dtoPayment)
                .retrieve()
                .bodyToMono(PaymentDtoResponse.class)
                .onErrorResume(error -> {
                    System.err.println("Erro ao criar pagamento:" + error.getMessage());
                    System.out.println("DTO enviado -> amount: " + dtoPayment.getAmount()
                            + ", method: " + dtoPayment.getPaymentMethod()
                            + ", status: " + dtoPayment.getPaymentStatus());
                    return Mono.empty();
                });
    }

}
