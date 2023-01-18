package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao productDao;


    public void add(ProductPojo p) throws ApiException {
        if(checkBarcode(p.getBarcode())==false){
            throw new ApiException("Barcode already exists");
        }
        productDao.insert(p);
    }


    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }


    public List<ProductPojo> getAll() {
        return productDao.selectALL(ProductPojo.class);
    }


    public void update(int id, ProductPojo p) throws ApiException {
        ProductPojo px = get(id);
        if(checkBarcode(p.getBarcode())==false) {
            px.setName(p.getName());
            px.setMrp(p.getMrp());
            productDao.update();
        }
    }

    public Boolean checkBarcode(String barcode){
        ProductPojo p = productDao.selectByBarcode(barcode);
        if(Objects.isNull(p)) {
            return true;
        }
        return false;
    }


    public ProductPojo getByBarcode(String barcode) throws ApiException {
        ProductPojo p = productDao.selectByBarcode(barcode);
        if(Objects.isNull(p)) {
            throw new ApiException("Product with given Barcode does not exists !");
        }
        return p;
    }

    public ProductPojo getCheck(int id) throws ApiException{
        ProductPojo p = productDao.selectByID(id, ProductPojo.class);
        if(Objects.isNull(p)) {
            throw new ApiException("Product with given ID does not exists !");
        }
        return p;
    }

    public Integer getIDByBarcode(String barcode) throws ApiException {
        ProductPojo p = productDao.selectByBarcode(barcode);
        if(Objects.isNull(p)) {
            throw new ApiException("Barcode Not found in Product Database!");
        }
        return p.getId();
    }
}
