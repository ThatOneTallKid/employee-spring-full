package com.increff.employee.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrderItemForm {

    @NotBlank(message = "Barcode cannot be empty")
    @Size(min = 8, max = 8, message = "Barcode must 8 character long")
    private String barcode;

    @NotNull(message = "Quantity cannot be blank")
    @Min(value = 1, message = "Quantity must be atleast 1")
    private int qty;

    @NotNull(message = "Selling Price cannot be blank")
    @Min(value = 1, message = "Selling Price must be atleast 1")
    private Double sellingPrice;
}
