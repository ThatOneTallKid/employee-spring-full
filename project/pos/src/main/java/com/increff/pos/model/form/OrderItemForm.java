package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrderItemForm {

    @NotBlank
    @Size(min = 1, max = 15, message = " must 1 to 15 character long")
    private String barcode;

    @NotNull
    @Min(value = 1, message = " must be atleast 1")
    private int qty;

    @NotNull
    @Min(value = 1, message = " must be atleast 1")
    private double sellingPrice;


}
