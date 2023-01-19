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


    public void add(ProductPojo productPojo) throws ApiException {
        if(checkBarcode(productPojo.getBarcode())==false){
            throw new ApiException("Barcode already exists");
        }
        productDao.insert(productPojo);
    }


    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }


    public List<ProductPojo> getAll() {
        return productDao.selectALL(ProductPojo.class);
    }


    public void update(int id, ProductPojo newProductPojo) throws ApiException {
        ProductPojo productPojo = get(id);
        if(checkBarcode(newProductPojo.getBarcode())==false) {
            productPojo.setName(newProductPojo.getName());
            productPojo.setMrp(newProductPojo.getMrp());
            productDao.update();
        }
    }

    public Boolean checkBarcode(String barcode){
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if(Objects.isNull(productPojo)) {
            return true;
        }
        return false;
    }


    public ProductPojo getByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if(Objects.isNull(productPojo)) {
            throw new ApiException("Product with given Barcode does not exists !");
        }
        return productPojo;
    }

    public ProductPojo getCheck(int id) throws ApiException{
        ProductPojo productPojo = productDao.selectByID(id, ProductPojo.class);
        if(Objects.isNull(productPojo)) {
            throw new ApiException("Product with given ID does not exists !");
        }
        return productPojo;
    }

    public Integer getIDByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if(Objects.isNull(productPojo)) {
            throw new ApiException("Barcode Not found in Product Database!");
        }
        return productPojo.getId();
    }
}
