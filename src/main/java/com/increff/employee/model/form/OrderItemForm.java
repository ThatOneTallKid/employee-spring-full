package com.increff.employee.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemForm {

    @NotNull
    private String barcode;

    @NotNull
    private int qty;

    @NotNull
    private int sellingPrice;
}
