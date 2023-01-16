package com.increff.employee.model.data;

import com.increff.employee.model.form.OrderItemForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class OrderItemData extends OrderItemForm {


    private int id;

    private int orderId;

    private int productId;

    private String name;
}
