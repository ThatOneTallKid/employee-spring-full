package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.ValidationUtil;
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
        dao.insert(b);
    }


    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        BrandPojo p = dao.selectByID(id, BrandPojo.class, "BrandPojo");
        if (ValidationUtil.checkNull(p)) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return dao.selectALL(BrandPojo.class, "BrandPojo");
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, BrandPojo b) throws ApiException{
        BrandPojo bx = get(id);
        if(checkBrandExists(b.getBrand(), b.getCategory()) == true) {
            throw new ApiException("Same brand and category exist");
        }
        bx.setCategory(b.getCategory());
        bx.setBrand(b.getBrand());
        dao.update();
    }

    @Transactional
    public Boolean checkBrandExists(String brand, String category)  {
        BrandPojo b = dao.selectByBarcodeCategory(brand, category);
        if(ValidationUtil.checkNull(b)) {
            return false;
        }
        return true;
    }

    @Transactional
    public BrandPojo getBrandPojofromBrandCategory(String brand, String category) throws ApiException {
        BrandPojo b = dao.selectByBarcodeCategory(brand, category);
        return b;
    }
}
