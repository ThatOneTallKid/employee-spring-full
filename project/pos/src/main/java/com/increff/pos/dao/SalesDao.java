package com.increff.pos.dao;

import com.increff.pos.pojo.SalesPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;

@Repository
public class SalesDao extends AbstractDao{
    private final String SELECT_BY_DATE = "select p from SalesPojo p where date=:date";

    public SalesPojo selectByDate(LocalDate date) {
        TypedQuery<SalesPojo> query = getQuery(SELECT_BY_DATE, SalesPojo.class);
        query.setParameter("date", date);
        return query.getResultStream().findFirst().orElse(null);
    }
}
