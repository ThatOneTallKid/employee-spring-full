package com.increff.employee.model.form;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class BrandForm {

    @NotNull
    private String brand;
    @NotNull
    private String category;
}
