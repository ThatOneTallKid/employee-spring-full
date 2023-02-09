package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class OrderDao extends AbstractDao{

    private final String SELECT_BY_DATE_FILTER = "select p from OrderPojo p where createdAt>=:startDate and createdAt<=:endDate";

    public void update() {

    }

    public List<OrderPojo> getOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_DATE_FILTER, OrderPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}
