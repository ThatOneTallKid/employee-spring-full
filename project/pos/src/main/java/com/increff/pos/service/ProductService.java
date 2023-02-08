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
        checkSame(productPojo.getBarcode());
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
        if(productPojo.getBarcode().contentEquals(newProductPojo.getBarcode())) {
            productPojo.setName(newProductPojo.getName());
            productPojo.setMrp(newProductPojo.getMrp());
            productDao.update();
        }
        else {
            throw new ApiException("Barcode cannot be changed");
        }
    }

    public List<ProductPojo> selectInBarcode(List<String> barcode) throws ApiException {
        List<ProductPojo> productPojoList = productDao.selectInBarcode(barcode);
        String error = "Following Barcode not found in Product Database: ";
        for (String s : barcode) {
            if (!productPojoList.stream().anyMatch(productPojo -> productPojo.getBarcode().contentEquals(s))) {
                error += s + ", ";
            }
        }
        if(productPojoList.size() != barcode.size()){
            throw new ApiException(error);
        }
        return productPojoList;
    }

    public void checkSame(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if (!Objects.isNull(productPojo)){
            throw new ApiException("Same Barcode: "+barcode +" already exists");
        }

    }

    public ProductPojo getCheck(int id) throws ApiException{
        ProductPojo productPojo = productDao.selectByID(id, ProductPojo.class);
        if(Objects.isNull(productPojo)) {
            throw new ApiException("Product with ID: "+id+" does not exists !");
        }
        return productPojo;
    }

    public ProductPojo getByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if(Objects.isNull(productPojo)) {
            throw new ApiException("Barcode: "+barcode+" not found in Product Database!");
        }
        return productPojo;
    }

}
