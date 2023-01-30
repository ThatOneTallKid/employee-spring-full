package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class OrderItemDao extends AbstractDao{

    private final String DELETE_BY_ID = "delete from OrderItemPojo where id=:id";
    private final String SELECT_BY_ORDER_ID = "select o from OrderItemPojo o where orderId=:orderId";
    private final String SELECT_BY_ORDER_ID_PRODUCT_ID = "select o from OrderItemPojo o where orderId=:orderId and" +
            " productId=:productId";

    public OrderItemPojo deleteById(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(DELETE_BY_ID, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public void update() {

    }

    public List<OrderItemPojo> selectOrderItemByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

}
