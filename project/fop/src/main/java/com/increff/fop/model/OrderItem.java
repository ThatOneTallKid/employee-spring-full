package com.increff.fop.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private Integer orderItemId;
    private Double sellingPrice;
    private Double amt;
    private String productName;
    private Integer quantity;
}