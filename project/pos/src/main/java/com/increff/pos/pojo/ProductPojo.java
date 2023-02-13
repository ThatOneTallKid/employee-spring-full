package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(uniqueConstraints =
        { //other constraints
                @UniqueConstraint(name = "uniqueBarcode", columnNames = { "barcode" })})
public class ProductPojo extends AbstractPojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String barcode;


    @Column(nullable = false)
    private int brandCategory;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double mrp;
}
