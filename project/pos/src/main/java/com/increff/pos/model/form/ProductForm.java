package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class ProductForm {

    @NotBlank
    @Size(min = 1, max = 15,message = " must be between 1 and 15 characters ")
    private String barcode;

    @NotBlank
    @Size(min = 1, max = 15,message = " must be between 1 and 15 characters long ")
    private String brand;

    @NotBlank
    @Size(min = 1, max = 15,message = " must be between 1 and 15 characters long ")
    private String category;

    @NotBlank
    @Size(min = 1, max = 15,message = " must be at least 1 character long ")
    private String name;


    @NotNull
    @Min(value = 0, message = " must be atleast 0 ")
    @Max(value = 1000000, message = " must be atmost 10,00,000 ")
    private double mrp;
}
