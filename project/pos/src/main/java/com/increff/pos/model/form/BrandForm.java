package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

//TODO remove messages from every form
@Getter
@Setter
public class BrandForm {

    @NotBlank
    private String brand;

    @NotBlank(message = "Category cannot be empty")
    private String category;
}
