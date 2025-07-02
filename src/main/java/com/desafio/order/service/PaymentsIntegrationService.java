package com.desafio.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentsIntegrationService {

    private final WebClient webClient;

    public PaymentsIntegrationService(WebClient webClientPayments) {
        this.webClient = webClientPayments;
    }
}
