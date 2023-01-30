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

    @Column(nullable = false,unique = true)
    private String barcode;


    @Column(nullable = false)
    private int brandCategory;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double mrp;
}
