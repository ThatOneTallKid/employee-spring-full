package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public void add(OrderPojo o) {
        orderDao.insert(o);
    }

    public OrderPojo get(int id) throws ApiException {
        OrderPojo o = orderDao.selectByID(id, OrderPojo.class);
        if(Objects.isNull(o)) {
            throw new ApiException("Order does not exists");
        }
        return o;
    }

    public List<OrderPojo> getAll() {
        return orderDao.selectALL(OrderPojo.class);
    }


}
