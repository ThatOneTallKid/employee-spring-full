package com.increff.employee.service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;


    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        if(checkBarcode(p.getBarcode()) == false ){
            throw new ApiException("Barcode already exists");
        }
        productDao.insert(p);
    }


    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectALL(ProductPojo.class, "ProductPojo");
    }


    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        ProductPojo px = getCheck(id);
        if(checkBarcode(p.getBarcode()) == false) {
            px.setBarcode(p.getBarcode());
            px.setBrand_category(p.getBrand_category());
            px.setName(p.getName());
            px.setMrp(p.getMrp());
            productDao.update();
        }
        else {
            throw new ApiException("Barcode already exists");
        }
    }

    @Transactional
    public Boolean checkBarcode(String barcode){
        ProductPojo p = productDao.selectByBarcode(barcode);
        if(ValidationUtil.checkNull(p)) {
            return true;
        }
        return false;
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException{
        ProductPojo p = productDao.selectByID(id, ProductPojo.class, "ProductPojo");
        if(ValidationUtil.checkNull(p)) {
            throw new ApiException("Product with given ID does not exists !");
        }
        return p;
    }

    @Transactional
    public Integer getIDByBarcode(String barcode) throws ApiException {
        ProductPojo p = productDao.selectByBarcode(barcode);
        if(ValidationUtil.checkNull(p)) {
            throw new ApiException("Barcode Not found in Product Database!");
        }
        return p.getId();
    }

    @Transactional
    public String getBarcodeByID(int id) throws ApiException {
        ProductPojo p = productDao.selectByID(id, ProductPojo.class, "ProductPojo");
        System.out.println(p.getId());
        if(ValidationUtil.checkNull(p)) {
            throw new ApiException("Barcode Not found in Product Database!");
        }
        return p.getBarcode();
    }
}
