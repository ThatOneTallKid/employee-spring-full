package com.increff.pos.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "pos_day_sales")
public class SalesPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonFormat(pattern="yyyy-MM-dd ")
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private int invoicedOrderCount;
    @Column(nullable = false)
    private int invoicedItemsCount;
    @Column(nullable = false)
    private double totalRevenue;
    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastRun;

}
