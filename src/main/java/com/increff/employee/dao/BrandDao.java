package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
public class BrandDao extends AbstractDao{

    private static String SELECT_BY_BRAND_CATEGORY = "select b from BrandPojo b where brand=:brand and " +
            "category=:category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo b) {
        // saves object into the db
        em.persist(b);
    }

    public BrandPojo selectByBarcodeCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }

    public  void update(){

    }
}
