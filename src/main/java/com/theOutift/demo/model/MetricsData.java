package com.theOutift.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MetricsData {

    private double averageSoldProductsPerOrder;
    private List<Product> top10SoldProducts;
    private List<String> top3Brands;
}
