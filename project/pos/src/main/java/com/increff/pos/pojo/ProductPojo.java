package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProductPojo extends AbstractPojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String barcode;
    //TODO use camelCase

    @NotNull
    private int brandCategory;

    @NotNull
    private String name;

    @NotNull
    private Double mrp;
}
