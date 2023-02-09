package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class OrderItemPojo extends AbstractPojo{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int orderId;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private double sellingPrice;
}
