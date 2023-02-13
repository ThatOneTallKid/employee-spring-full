package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(uniqueConstraints =
        { //other constraints
                @UniqueConstraint(name = "UniqueBrandAndCategory", columnNames = { "brand", "category" })})
public class BrandPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category;

}
