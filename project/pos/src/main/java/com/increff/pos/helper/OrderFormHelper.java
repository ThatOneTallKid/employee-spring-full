package com.increff.pos.helper;

import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.form.OrderForm;
import com.increff.pos.pojo.OrderPojo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OrderFormHelper {



    public static OrderPojo convertOrderFormToPojo(OrderForm f) {
        return new OrderPojo();
    }

    public static OrderData convertOrderPojoToData(OrderPojo orderPojo) {
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        Timestamp timestamp = orderPojo.getCreatedAt();
        orderData.setOrderDate(LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.ofHoursMinutes(5, 30)));
        return orderData;

    }
}
