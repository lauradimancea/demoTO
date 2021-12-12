package com.theOutift.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Product {

    private long productId;

    private String brand;

    @JsonIgnore
    private double timesSold;

    @JsonIgnore
    private double timesOrdered;

    private double purchaseProbability;
}
