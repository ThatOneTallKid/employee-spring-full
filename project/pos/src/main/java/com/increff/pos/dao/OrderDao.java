package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
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
