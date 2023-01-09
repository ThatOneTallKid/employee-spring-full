package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo b) throws ApiException{
        normalize(b);
        if(StringUtil.isEmpty(b.getCategory()) || StringUtil.isEmpty(b.getBrand())) {
            throw  new ApiException("Brand or Category cannot be empty");
        }
        if(getCheck(b.getBrand(), b.getCategory()) == null) {
            dao.insert(b);
        }
        else {
            throw  new ApiException("Item already exists !");
        }
    }


    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return dao.selectALL(BrandPojo.class, "BrandPojo");
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, BrandPojo b) throws ApiException{
        normalize(b);
        BrandPojo bx = getCheck(id);
        if(BCheck(b.getBrand(), b.getCategory()) == true) {
            throw new ApiException("Same brand and category exist");
        }
        bx.setCategory(b.getCategory());
        bx.setBrand(b.getBrand());
        dao.update();
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = dao.selectByID(id, BrandPojo.class, "BrandPojo");
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }

    /*
    *       Returns: Boolean
    *       True if an object with brand b and category c exists, else false
    * */
    @Transactional
    public Boolean BCheck(String brand, String category)  {
        BrandPojo b = dao.selectByBarcodeCategory(brand, category);
        if(b == null) {
            return false;
        }
        return true;
    }

    @Transactional
    public Integer IDFetcher(String brand, String category) throws ApiException {
        BrandPojo b = dao.selectByBarcodeCategory(brand, category);
        if(b == null) {
            throw new ApiException("Brand ID with corresponding brand and category does not exists");
        }
        return b.getId();
    }

    @Transactional
    public BrandPojo getCheck(String brand, String category) throws ApiException {
        BrandPojo b = dao.selectByBarcodeCategory(brand, category);

        return b;
    }


    // converts Brandname and Categoryname to lower case
    protected static void normalize(BrandPojo b) {
        b.setBrand(StringUtil.toLowerCase(b.getBrand()));
        b.setCategory(StringUtil.toLowerCase(b.getCategory()));
    }
}
