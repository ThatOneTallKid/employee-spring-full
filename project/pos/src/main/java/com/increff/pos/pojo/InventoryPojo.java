package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class InventoryPojo extends AbstractPojo {

    // Product ID
    @Id
    private int id;

    @Column(nullable = false)
    private int qty;

}
