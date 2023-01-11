package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class OrderDao extends AbstractDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo o) {
        em.persist(o);
    }

    public void update() {

    }
}
