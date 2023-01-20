package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;


    public void add(OrderItemPojo orderItemPojo) throws ApiException {

        orderItemDao.insert(orderItemPojo);
    }

    public OrderItemPojo get(int id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemDao.selectByID(id, OrderItemPojo.class);
        if(Objects.isNull(orderItemPojo)){
            throw new ApiException("Order Item with given Id does not exists.;");
        }
        return orderItemPojo;
    }

    public List<OrderItemPojo> getAll() {
        return orderItemDao.selectALL(OrderItemPojo.class);
    }

    public void update(int id, OrderItemPojo newOrderItemPojo) throws ApiException{
        OrderItemPojo orderItemPojo = get(id);
        orderItemPojo.setQty(newOrderItemPojo.getQty());
        orderItemPojo.setSellingPrice(newOrderItemPojo.getSellingPrice());
        orderItemDao.update();
    }

    public OrderItemPojo getOrderItemByOrderId(int orderId) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemDao.selectByOrderId(orderId);
        if(Objects.isNull(orderItemPojo)){
            throw new ApiException("Order Item with given orderId does not exists.;");
        }
        return orderItemPojo;
    }

    public List<OrderItemPojo> getOrderItemsByOrderId(int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojo = orderItemDao.selectOrderItemByOrderId(orderId);

        return orderItemPojo;
    }

    public List<OrderItemPojo> getOrderItemByOrderItem(int orderId) throws ApiException {
        return orderItemDao.selectOrderByOrderId(orderId);
    }

    public OrderItemPojo getOrderItemByOrderIdProductId(int orderId,int productId) throws ApiException {
        return orderItemDao.selectByOrderIdProductId(orderId, productId);
    }

}
