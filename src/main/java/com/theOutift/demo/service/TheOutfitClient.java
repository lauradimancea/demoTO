package com.theOutift.demo.service;

import com.theOutift.demo.model.OrdersResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TheOutfitClient {

    private static final String THE_OUTFIT_BASE_URL = "https://op-app.azurewebsites.net/api/dev-test/get-products";

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public TheOutfitClient(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    public OrdersResponse[] getAllOrders() {

        try {
            return restTemplate.exchange(THE_OUTFIT_BASE_URL, HttpMethod.GET, new HttpEntity<String>(httpHeaders), OrdersResponse[].class).getBody();
        } catch (Exception e) {
            return new OrdersResponse[]{};
        }
    }
}
