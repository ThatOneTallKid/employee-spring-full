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


    public void add(OrderItemPojo o) throws ApiException {

        orderItemDao.insert(o);
    }

    public OrderItemPojo get(int id) throws ApiException {
        OrderItemPojo o = orderItemDao.selectByID(id, OrderItemPojo.class);
        if(Objects.isNull(o)){
            throw new ApiException("Order Item with given Id does not exists.;");
        }
        return o;
    }

    public List<OrderItemPojo> getAll() {
        return orderItemDao.selectALL(OrderItemPojo.class);
    }

    public void update(int id, OrderItemPojo o) throws ApiException{
        OrderItemPojo ox = get(id);
        ox.setQty(o.getQty());
        ox.setSellingPrice(o.getSellingPrice());
        orderItemDao.update();
    }

    public OrderItemPojo getOrderItemByOrderId(int orderId) throws ApiException {
        OrderItemPojo o = orderItemDao.selectByOrderId(orderId);
        if(Objects.isNull(o)){
            throw new ApiException("Order Item with given orderId does not exists.;");
        }
        return o;
    }

    public List<OrderItemPojo> getOrderItemByOrderItem(int orderId) throws ApiException {
        return orderItemDao.selectOrderByOrderId(orderId);
    }

    public OrderItemPojo getOrderItemByOrderIdProductId(int orderId,int productId) throws ApiException {
        OrderItemPojo o = orderItemDao.selectByOrderIdProductId(orderId, productId);
        return o;
    }

}
