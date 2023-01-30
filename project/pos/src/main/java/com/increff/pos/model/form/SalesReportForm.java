package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SalesReportForm {

    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;
    @NotBlank
    private String brand;
    @NotBlank
    private String category;
}
