package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao brandDao;


    public void add(BrandPojo brandPojo) throws ApiException{
            brandDao.insert(brandPojo);
    }

    public BrandPojo getCheck(Integer id) throws ApiException {
        BrandPojo brandPojo = brandDao.selectByID(id, BrandPojo.class);
        if (Objects.isNull(brandPojo)) {
            throw new ApiException("Brand with given ID does not exist, id: " + id);
        }
        return brandPojo;
    }

    public List<BrandPojo> getAll() {
        return brandDao.selectALL(BrandPojo.class);
    }

    public void update(Integer id, BrandPojo newBrandPojo) throws ApiException{
        BrandPojo brandPojo = getCheck(id);
        checkBrandExists(newBrandPojo.getBrand(), newBrandPojo.getCategory());
        brandPojo.setCategory(newBrandPojo.getCategory());
        brandPojo.setBrand(newBrandPojo.getBrand());
    }

    public void checkBrandExists(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandDao.selectByBrandCategory(brand, category);
        if(Objects.isNull(brandPojo)== false) {
            throw new ApiException("Brand: " + brand + " and Category: " + category+" already exists");
        }

    }

    public BrandPojo getBrandByParams(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandDao.selectByBrandCategory(brand, category);
        if(Objects.isNull(brandPojo)) {
            throw new ApiException("Brand: " + brand + " and Category: " + category+" does not exists");
        }
        return brandPojo;
    }

}
