package com.increff.pos.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "pos_day_sales")
public class SalesPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonFormat(pattern="yyyy-MM-dd ")
    @Column(nullable = false, name = "date")
    private LocalDate date;
    @Column(nullable = false, name = "invoiced_orders_count")
    private Integer invoicedOrderCount;
    @Column(nullable = false, name = "invoiced_items_count")
    private Integer invoicedItemsCount;
    @Column(nullable = false, name = "total_revenue")
    private Double totalRevenue;

}
