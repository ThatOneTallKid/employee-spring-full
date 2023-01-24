package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SalesReportData {

    private String brand;
    private String category;
    private int quantity;
    private double revenue;

    public SalesReportData() {
        this.setQuantity(0);
        this.setRevenue(0.0);
    }
}
