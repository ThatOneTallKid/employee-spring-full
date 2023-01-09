package com.increff.employee.dao;

import com.increff.employee.pojo.InventoryPojo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class InventoryDao extends AbstractDao{
//
//    private static String SELECT_BY_ID = "select i from InventoryPojo i where id=:id";
//    private static String SELECT_ALL = "select i from InventoryPojo i";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo i){
        em.persist(i);
    }

//    // Selets the InventoryPojo I by ID
//    public InventoryPojo select(int id) {
//        TypedQuery<InventoryPojo> query = getQuery(SELECT_BY_ID, InventoryPojo.class);
//        query.setParameter("id", id);
//        return getSingle(query);
//    }

    // Selects All the objects in the Inventory
//    public List<InventoryPojo> selectAll() {
//        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
//        return query.getResultList();
//    }

    public void update() {
        // Empty funtion just for service layer
    }
}
