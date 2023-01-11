package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.util.ValidationUtil;
import io.swagger.annotations.Api;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderPojo o) {
        orderDao.insert(o);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo get(int id) throws ApiException {
        OrderPojo o = orderDao.selectByID(id, OrderPojo.class, "OrderPojo");
        if(ValidationUtil.checkNull(o)) {
            throw new ApiException("Order does not exists");
        }
        return o;
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<OrderPojo> getAll() {
        return orderDao.selectALL(OrderPojo.class, "OrderPojo");
    }


}
