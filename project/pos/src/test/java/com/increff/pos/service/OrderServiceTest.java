package com.increff.pos.service;

import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderServiceTest extends AbstractUnitTest{
    @Autowired
    OrderService orderService;

    @Test
    public void checkOrderTest() throws ApiException {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(1);
        orderItemPojo.setQty(2);
        orderItemPojo.setSellingPrice(23.00);
        orderItemPojo.setProductId(5);

        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(2);
        orderItemPojo1.setQty(2);
        orderItemPojo1.setSellingPrice(23.00);
        orderItemPojo1.setProductId(5);

        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(2);
        orderItemPojo2.setQty(2);
        orderItemPojo2.setSellingPrice(23.00);
        orderItemPojo2.setProductId(5);

        orderService.add(orderItemPojo);
        orderService.add(orderItemPojo1);
        orderService.add(orderItemPojo2);

        OrderPojo orderPojo1 = new OrderPojo();
        orderPojo1.setId(1);

        OrderPojo orderPojo2 = new OrderPojo();
        orderPojo2.setId(2);

        orderService.addOrder(orderPojo1);
        orderService.addOrder(orderPojo2);

        List<OrderPojo> orderPojoList = orderService.getAllOrders();
        List<OrderItemPojo> orderItemPojoList1 = orderService.getOrderItemsByOrderId(2);
        List<OrderItemPojo> orderItemPojoList = orderService.getAll();

        assertEquals(3,orderItemPojoList.size());
        assertEquals(2,orderItemPojoList1.size());
        assertEquals(2,orderPojoList.size());

    }

    @Test
    public void checkOrder() throws ApiException {
        OrderPojo orderPojo1 = new OrderPojo();
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderPojo1.getId());
        orderItemPojo.setQty(2);
        orderItemPojo.setSellingPrice(23.00);
        orderItemPojo.setProductId(5);

        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(orderPojo1.getId());
        orderItemPojo1.setQty(2);
        orderItemPojo1.setSellingPrice(23.00);
        orderItemPojo1.setProductId(5);


        orderService.add(orderItemPojo);
        orderService.add(orderItemPojo1);



        orderService.addOrder(orderPojo1);

        OrderPojo orderPojo = orderService.getOrderById(orderPojo1.getId());
        assertEquals(orderPojo1.getId(), orderPojo.getId());

    }

    @Test
    public void testDateFilter() throws ApiException {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(1);
        orderItemPojo.setQty(2);
        orderItemPojo.setSellingPrice(23.00);
        orderItemPojo.setProductId(5);

        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(2);
        orderItemPojo1.setQty(2);
        orderItemPojo1.setSellingPrice(23.00);
        orderItemPojo1.setProductId(5);

        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(2);
        orderItemPojo2.setQty(2);
        orderItemPojo2.setSellingPrice(23.00);
        orderItemPojo2.setProductId(5);

        orderService.add(orderItemPojo);
        orderService.add(orderItemPojo1);
        orderService.add(orderItemPojo2);

        OrderPojo orderPojo1 = new OrderPojo();
        orderPojo1.setId(1);

        OrderPojo orderPojo2 = new OrderPojo();
        orderPojo2.setId(2);

        orderService.addOrder(orderPojo1);
        orderService.addOrder(orderPojo2);
        LocalDate date = LocalDate.now();
        LocalDateTime startDate = date.atStartOfDay();

        LocalDateTime endDate =  LocalDateTime.of(date, LocalTime.MAX);

        List<OrderPojo> orderPojoList = orderService.getOrderByDateFilter(startDate,endDate);
        assertEquals(2, orderPojoList.size());

    }
}
