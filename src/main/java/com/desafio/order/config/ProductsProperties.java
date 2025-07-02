package com.desafio.order.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ProductsProperties {

    @Value("${api.base.url.products}")
    private String baseUrlProducts;
}
