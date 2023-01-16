package com.increff.employee.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


// TODO add unique constrainsts and indexes
@Entity
@Getter
@Setter
@Table(uniqueConstraints =
        { //other constraints
                @UniqueConstraint(name = "UniqueBrandAndCategory", columnNames = { "brand", "category" })})
public class BrandPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String brand;

    @NotNull
    private String category;

}
