package com.increff.employee.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;


// TODO add unique constrainsts and indexes
@Entity
@Getter
@Setter
public class BrandPojo {

    // TODO about generation types
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min=1)
    private String brand;

    @NotNull
    @Size(min=1)
    private String category;

}
