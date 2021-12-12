package com.theOutift.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpHeaders createHeaders(@Value("${the.outfit.api.header.key}") String apiKey,
                                     @Value("${the.outfit.api.header.value}") String apiKeyValue) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(apiKey, apiKeyValue);
        return httpHeaders;
    }
}
