package com.increff.employee.model.data;

import com.increff.employee.model.form.OrderForm;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderData {
    private int id;
    private Date orderDate;
}
