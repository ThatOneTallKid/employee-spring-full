package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public void add(OrderPojo orderPojo) {
        orderDao.insert(orderPojo);
    }

    public OrderPojo get(int id) throws ApiException {
        OrderPojo orderPojo = orderDao.selectByID(id, OrderPojo.class);
        if(Objects.isNull(orderPojo)) {
            throw new ApiException("Order does not exists");
        }
        return orderPojo;
    }

    public List<OrderPojo> getAll() {
        return orderDao.selectALL(OrderPojo.class);
    }


    public List<OrderPojo> getOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate) throws ApiException {
        return orderDao.getOrderByDateFilter(startDate,endDate);
    }
}
