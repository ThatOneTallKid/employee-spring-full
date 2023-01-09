package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private BrandDao bdao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        normalize(p);
        if(StringUtil.isEmpty(p.getBarcode())){
            throw  new ApiException("Barcode cannot be empty");
        }
        if(p.getBrand_category() == 0){
            throw  new ApiException("Brand_Category cannot be empty");
        }
        if(StringUtil.isEmpty(p.getName())){
            throw  new ApiException("Name cannot be empty");
        }
        if(p.getMrp() == 0.0d){
            throw  new ApiException("Mrp cannot be empty");
        }

        if(checkBarcode(p.getBarcode()) == true ){

            if(checkBrand(p.getBrand_category()) == true) {
                dao.insert(p);
            }
            else {
                throw new ApiException("Brand_cateory does not exists in brand Database");
            }
        }
        else {
            throw new ApiException("Barcode already exists");
        }

    }


    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectALL(ProductPojo.class, "ProductPojo");
    }


    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo px = getCheck(id);
        if(checkBarcode(p.getBarcode()) == false){
            if(checkBrand(p.getBrand_category()) == true) {
                px.setBarcode(p.getBarcode());
                px.setBrand_category(p.getBrand_category());
                px.setName(p.getName());
                px.setMrp(p.getMrp());
                dao.update();
            }
            else {
                throw new ApiException("Brand_cateory does not match");
            }
        }
        else {
            throw new ApiException("Barcode already exists");
        }
    }

    @Transactional
    public Boolean checkBarcode(String barcode){
        ProductPojo p = dao.selectByBarcode(barcode);
        if(p == null) {
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean checkBrand(int brand_category) {
        BrandPojo b = bdao.selectByID(brand_category, BrandPojo.class, "BrandPojo");
        if(b == null) {
            return false;
        }
        return true;
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException{
        ProductPojo p = dao.selectByID(id, ProductPojo.class, "ProductPojo");
        if(p == null) {
            throw new ApiException("Product with given ID does not exists !");
        }
        return p;
    }


    protected static void normalize(ProductPojo p) {
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
        p.setName(StringUtil.toLowerCase(p.getName()));
    }
}
