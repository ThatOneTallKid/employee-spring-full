package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

//TODO replqce @NotNull with @column(nullable=false)
@Entity
@Setter
@Getter
public class OrderItemPojo extends AbstractPojo{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int orderId;

    @NotNull
    private int productId;

    @NotNull
    private int qty;

    //TODO rename sellingPrice
    @NotNull
    private Double SellingPrice;
}
