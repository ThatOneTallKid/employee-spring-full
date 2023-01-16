package com.increff.employee.dto;

import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.form.OrderForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.helper.OrderFormHelper.convertOrderFormToPojo;
import static com.increff.employee.helper.OrderFormHelper.convertOrderPojoToData;

@Configuration
public class OrderDto {

    @Autowired
    OrderService orderService;

    public void add(OrderForm f) throws ApiException {
        OrderPojo o = convertOrderFormToPojo(f);
        orderService.add(o);
    }

    public OrderData get(int id) throws ApiException {
        OrderPojo o = orderService.get(id);
        return convertOrderPojoToData(o);
    }

    public List<OrderData> getAll() {
        List<OrderPojo> list = orderService.getAll();
        List<OrderData> list2 = new ArrayList<>();
        for(OrderPojo o : list) {
            list2.add(convertOrderPojoToData(o));
        }
        return list2;
    }
}
