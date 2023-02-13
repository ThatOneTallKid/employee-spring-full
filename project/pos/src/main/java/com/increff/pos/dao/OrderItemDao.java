package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class OrderItemDao extends AbstractDao{

    private final String SELECT_BY_ORDER_ID = "select o from OrderItemPojo o where orderId=:orderId";

    public List<OrderItemPojo> selectOrderItemByOrderId(Integer orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

}
