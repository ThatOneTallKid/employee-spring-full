package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
public class InventoryPojo extends AbstractPojo {

    // Product ID
    @Id
    private int id;

    @NotNull
    @Min(value = 1)
    private int qty;

}
