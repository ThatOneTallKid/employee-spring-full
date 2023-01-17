package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao dao;


    public void add(BrandPojo b) throws ApiException{
        dao.insert(b);
    }

    public BrandPojo get(int id) throws ApiException {
        BrandPojo p = dao.selectByID(id, BrandPojo.class);
        if (Objects.isNull(p)) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }

    public List<BrandPojo> getAll() {
        return dao.selectALL(BrandPojo.class);
    }

    public void update(int id, BrandPojo b) throws ApiException{
        BrandPojo bx = get(id);
        if(checkBrandExists(b.getBrand(), b.getCategory()) == true) {
            throw new ApiException("Same brand and category exist");
        }
        bx.setCategory(b.getCategory());
        bx.setBrand(b.getBrand());
        dao.update();
    }

    public Boolean checkBrandExists(String brand, String category)  {
        BrandPojo b = dao.selectByBrandCategory(brand, category);
        if(Objects.isNull(b)) {
            return false;
        }
        return true;
    }

    public BrandPojo getBrandPojofromBrandCategory(String brand, String category) throws ApiException {
        BrandPojo b = dao.selectByBrandCategory(brand, category);
        return b;
    }
}
