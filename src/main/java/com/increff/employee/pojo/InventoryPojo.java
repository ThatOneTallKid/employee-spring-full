package com.increff.employee.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class InventoryPojo extends AbstractPojo {

    // Product ID
    @Id
    private int id;

    @NotNull
    private int qty;

}
