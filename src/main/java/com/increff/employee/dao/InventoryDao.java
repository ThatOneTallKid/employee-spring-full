package com.increff.employee.dao;

import com.increff.employee.pojo.InventoryPojo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class InventoryDao extends AbstractDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo i){
        em.persist(i);
    }

    public void update() {
        // Empty funtion just for service layer
    }
}
