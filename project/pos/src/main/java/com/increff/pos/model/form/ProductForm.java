package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductForm {

    @NotBlank
    @Size(min = 1, message = "Barcode must be at least 1 character long")
    private String barcode;

    @NotBlank
    private String brand;

    @NotBlank
    private String category;

    @NotBlank
    private String name;


    @NotNull
    @Min(value = 1, message = "Mrp must be atleast 1")
    private double mrp;
}
