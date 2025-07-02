package com.desafio.order.config;

import io.netty.channel.ChannelOption;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@AllArgsConstructor
public class WebClientConfig {

    private final PaymentsProperties paymentsProperties;
    private final ProductsProperties productsProperties;

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
    }

    @Bean
    public WebClient webClientProducts(WebClient.Builder webClientBuilder) {
        return WebClient.builder()
                .baseUrl(productsProperties.getBaseUrlProducts())
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .build();
    }

    @Bean
    public WebClient webClientPayments(WebClient.Builder webClientBuilder) {
        return WebClient.builder()
                .baseUrl(paymentsProperties.getBaseUrlPayments())
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .build();
    }
}
