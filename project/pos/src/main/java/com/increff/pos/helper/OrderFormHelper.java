package com.increff.pos.helper;

import com.increff.pos.model.data.OrderData;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.util.ConvertUtil;

public class OrderFormHelper {
    public static OrderData convertOrderPojoToData(OrderPojo orderPojo) {
        OrderData orderData = ConvertUtil.convert(orderPojo,OrderData.class);
        orderData.setOrderDate(orderPojo.getCreatedAt());
        return orderData;

    }
}
