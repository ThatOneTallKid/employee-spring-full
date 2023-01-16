package com.increff.employee.model.form;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BrandForm {

    @NotBlank(message = "Brand Cannot be Empty")
    private String brand;

    @NotBlank(message = "Category cannot be empty")
    private String category;
}
