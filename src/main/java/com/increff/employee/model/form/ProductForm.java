package com.increff.employee.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductForm {

    @NotBlank(message = "Barcode cannot be empty")
    @Size(min = 8, max = 8, message = "Barcode must 8 character long")
    private String barcode;

    @NotBlank(message = "Brand Cannot be Empty")
    private String brand;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotBlank(message = "Name cannot be empty")
    private String name;


    @NotNull(message = "Mrp cannot be blank")
    @Min(value = 0, message = "Mrp must be atleast 1")
    private Double mrp;
}
