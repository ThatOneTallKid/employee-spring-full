package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class OrderItemForm {

    @NotBlank
    @Size(min = 1, max = 15, message = " must 1 to 15 character long")
    private String barcode;

    @NotNull
    @Min(value = 1, message = " must be atleast 1")
    @Max(value = 250, message = " must be atmost 250")
    private int qty;

    @NotNull
    @Min(value = 0, message = " must be atleast 0")
    @Max(value = 1000000, message = " must be atmost 10,00,000")
    private double sellingPrice;


}
