package com.theOutift.demo.service;

import com.theOutift.demo.model.OrderStatus;
import com.theOutift.demo.model.OrdersResponse;
import com.theOutift.demo.model.Product;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricsService {

    private static final int ORDER_SIZE = 5;

    private final List<OrdersResponse> allOrders;
    private final Map<Long, Product> productsById;

    public MetricsService(TheOutfitClient theOutfitClient) {
        this.allOrders = Arrays.asList(theOutfitClient.getAllOrders());
        this.productsById = new HashMap<>();
        buildProducts();
    }

    public double getAverageProductsKeptPerOrder() {

        double productsKept = getProductsKeptCount();
        if (productsKept == 0) {
            return 0;
        }
        double allProductsOrdered = ORDER_SIZE * getOrdersCount();

        return allProductsOrdered/productsKept;
    }

    public List<Product> getMostSoldProducts() {

        return productsById.values().stream()
                .filter(p -> p.getTimesOrdered() > 5)
                .sorted(Comparator.comparingDouble(Product::getPurchaseProbability).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<String> getTop3Brands() {

        MultiValuedMap<String, Product> productByBrand = new ArrayListValuedHashMap<>();

        productsById.values().forEach(p -> {
            String brandName = p.getBrand();
            productByBrand.put(brandName, p);
        });

        Map<String, Double> purchaseProbabilityByBrand = new HashMap<>();
        calculatePurchaseProbabilityBrands(productByBrand, purchaseProbabilityByBrand);

        return purchaseProbabilityByBrand.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(Double::doubleValue).reversed()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private double getProductsKeptCount() {
        return allOrders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.VANDUT.name()))
                .count();
    }

    private double getOrdersCount() {
        return allOrders.stream().map(OrdersResponse::getOrderId).distinct().count();
    }

    private void buildProducts() {
        allOrders.forEach(order -> {
            long productId = order.getProductId();
            if (productsById.containsKey(productId)) {
                Product product = productsById.get(productId);
                double timesSold = product.getTimesSold();
                double timesOrdered = product.getTimesOrdered();
                product.setTimesOrdered(++timesOrdered);
                product.setTimesSold(++timesSold);
                productsById.put(productId, product);
            } else {
                productsById.put(productId, Product.builder()
                        .productId(productId)
                        .brand(order.getBrand())
                        .timesSold(order.getStatus().equals(OrderStatus.VANDUT.name()) ? 1 : 0)
                        .timesOrdered(1)
                        .build());
            }
        });
        productsById.values().forEach(p -> {
            if (p.getTimesOrdered() != 0) {
                p.setPurchaseProbability(p.getTimesSold() / p.getTimesOrdered());
            }
        });
    }

    private void calculatePurchaseProbabilityBrands(MultiValuedMap<String, Product> productByBrand,
                                                    Map<String, Double> purchaseProbabilityByBrand) {
        productByBrand.asMap().forEach((brand, products) -> {
            double productsSold = 0;
            double productsOrdered = 0;
            for (Product p : products) {
                productsSold += p.getTimesSold();
                productsOrdered += p.getTimesOrdered();
            }
            if (productsOrdered != 0) {
                purchaseProbabilityByBrand.put(brand, productsSold / productsOrdered);
            }
        });
    }
}
