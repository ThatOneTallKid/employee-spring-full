package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryErrorData {
    private String barcode;
    private int qty;
    private String message;
}
