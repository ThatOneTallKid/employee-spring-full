package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class InventoryForm {

    @NotBlank
    @Size(min = 1, message = "Barcode must be at least 1 character long")
    private String barcode;

    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private int qty;
}
