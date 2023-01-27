package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SalesForm {
    @NotBlank(message = "Start Date Cannot be empty")
    private String startDate;
    @NotBlank(message = "End Date Cannot be empty")
    private String endDate;
}
