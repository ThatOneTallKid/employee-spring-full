package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BrandForm {

    @NotBlank(message = "Brand Cannot be Empty")
    private String brand;

    @NotBlank(message = "Category cannot be empty")
    private String category;
}
