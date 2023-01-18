package com.increff.pos.helper;

import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.form.OrderForm;
import com.increff.pos.pojo.OrderPojo;

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
