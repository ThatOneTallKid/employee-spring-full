package com.increff.employee.helper;

import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.form.OrderForm;
import com.increff.employee.pojo.OrderPojo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OrderFormHelper {



    public static OrderPojo convertOrderFormToPojo(OrderForm f) {
        OrderPojo o = new OrderPojo();
        return o;
    }

    public static OrderData convertOrderPojoToData(OrderPojo p) {
        OrderData d = new OrderData();
        d.setId(p.getId());
        Timestamp timestamp = p.getCreatedAt();
        d.setOrderDate(LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.ofHoursMinutes(5, 30)));
        return d;

    }
}
