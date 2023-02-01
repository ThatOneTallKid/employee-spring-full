package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderDao orderDao;


    public void add(OrderItemPojo orderItemPojo) throws ApiException {

        orderItemDao.insert(orderItemPojo);
    }


    public List<OrderItemPojo> getAll() {
        return orderItemDao.selectALL(OrderItemPojo.class);
    }

    public List<OrderItemPojo> getOrderItemsByOrderId(int orderId){
        List<OrderItemPojo> orderItemPojo = orderItemDao.selectOrderItemByOrderId(orderId);
        return orderItemPojo;
    }


    public void addOrder(OrderPojo orderPojo) {
        orderDao.insert(orderPojo);
    }

    public OrderPojo getOrderById(int id) throws ApiException {
        OrderPojo orderPojo = orderDao.selectByID(id, OrderPojo.class);
        if(Objects.isNull(orderPojo)) {
            throw new ApiException("Order does not exists");
        }
        return orderPojo;
    }

    public List<OrderPojo> getAllOrders() {
        return orderDao.selectALL(OrderPojo.class);
    }


    public List<OrderPojo> getOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate)  {
        return orderDao.getOrderByDateFilter(startDate,endDate);
    }

}
