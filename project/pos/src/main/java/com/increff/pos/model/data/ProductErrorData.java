package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductErrorData {
    private String barcode;
    private String brand;
    private String category;
    private String Name;
    private Double mrp;
    private String message;
}
