package com.theOutift.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {

    private String orderId;
    private long productId;
    private String size;
    private String status;
    private String brand;

}
