package com.increff.pos.dao;

import com.increff.pos.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class BrandDao extends AbstractDao{

    // check final
    private static String SELECT_BY_BRAND_CATEGORY = "select b from BrandPojo b where brand=:brand and " +
            "category=:category";


    public BrandPojo selectByBrandCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }

    public  void update(){

    }
}
