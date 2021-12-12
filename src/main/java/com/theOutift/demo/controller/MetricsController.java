package com.theOutift.demo.controller;

import com.theOutift.demo.model.MetricsData;
import com.theOutift.demo.service.MetricsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/demo")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping(path = "/metrics", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<MetricsData> getDemoAnalytics() {

        MetricsData metricsData = new MetricsData();
        metricsData.setAverageSoldProductsPerOrder(metricsService.getAverageProductsKeptPerOrder());
        metricsData.setTop10SoldProducts(metricsService.getMostSoldProducts());
        metricsData.setTop3Brands(metricsService.getTop3Brands());

        return ResponseEntity.ok(metricsData);
    }
}
