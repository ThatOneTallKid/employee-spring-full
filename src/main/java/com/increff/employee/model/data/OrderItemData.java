package com.increff.employee.model.data;

import com.increff.employee.model.form.OrderItemForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class OrderItemData extends OrderItemForm {


    int id;


    // TODO: ADD it later in the course of time.
    int orderId;

    int productId;
}
