package com.increff.pos.dao;

import com.increff.pos.pojo.SalesPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SalesDao extends AbstractDao{
    private final String SELECT_BY_DATE = "select p from SalesPojo p where date=:date";
    private final String SELECT_ALL_BY_DATE = "select p from SalesPojo p where date>=:startDate and date<=:endDate";

    public SalesPojo selectByDate(LocalDate date) {
        TypedQuery<SalesPojo> query = getQuery(SELECT_BY_DATE, SalesPojo.class);
        query.setParameter("date", date);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<SalesPojo> getAllByDate(LocalDate startDate, LocalDate endDate) {
        TypedQuery<SalesPojo> query = getQuery(SELECT_ALL_BY_DATE, SalesPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}
