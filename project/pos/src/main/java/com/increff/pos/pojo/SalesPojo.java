package com.increff.pos.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

//TODO add snakeCaseNamingStratergy for hibernate
@Entity
@Getter
@Setter
@Table(name = "pos_day_sales")
public class SalesPojo extends AbstractPojo{
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
    @Column(nullable = false, name = "last_run")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastRun;

}
