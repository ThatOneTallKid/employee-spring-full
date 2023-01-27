package com.increff.invoiceapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BrandReportForm {
    private List<BrandData> brandItems;
}
