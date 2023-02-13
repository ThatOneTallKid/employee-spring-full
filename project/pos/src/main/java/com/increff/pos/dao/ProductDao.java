package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductDao extends AbstractDao{

    private final String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode=:barcode";
    private final String SELECT_IN_BARCODE = "select p from ProductPojo p where barcode in (:barcode)";

    public ProductPojo selectByBarcode(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public List<ProductPojo> selectInBarcode(List<String> barcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_IN_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return query.getResultList();
    }
}
