package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static String DELETE_BY_ID = "delete from OrderItemPojo where id=:id";
    private static String SELECT_BY_ORDER_ID = "select o from OrderItemPojo o where orderId=:orderId";
    private static String SELECT_BY_ORDER_ID_PRODUCT_ID = "select o from OrderItemPojo o where orderId=:orderId and productId=:productId";

    public OrderItemPojo deleteById(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(DELETE_BY_ID, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public void update() {

    }

    public OrderItemPojo selectByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectOrderByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    public OrderItemPojo selectByOrderIdProductId(int orderId,int productId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID_PRODUCT_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        query.setParameter("productId", productId);
        return getSingle(query);
    }
}
