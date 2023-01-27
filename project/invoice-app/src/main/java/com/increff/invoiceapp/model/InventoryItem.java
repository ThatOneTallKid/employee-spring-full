package com.increff.invoiceapp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryItem {
    private int id;
    private String brand;
    private String category;
    private int qty;

}
