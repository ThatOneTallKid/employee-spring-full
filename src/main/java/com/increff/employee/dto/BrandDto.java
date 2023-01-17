package com.increff.employee.dto;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.employee.helper.BrandFormHelper.*;


@Component
public class BrandDto {
    @Autowired
    BrandService brandService;

    public void add(BrandForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        BrandPojo b = convertBrandFormToPojo(f);
        normalizeBrand(b);
        if(Objects.isNull(brandService.getBrandPojofromBrandCategory(b.getBrand(), b.getCategory()))){
            brandService.add(b);
        }
        else {
            throw new ApiException("Brand and Category already exists !");
        }
    }

    public BrandData get(int id) throws ApiException{
        return convertBrandPojoToData(brandService.get(id));
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<>();
        for(BrandPojo b : list) {
            list2.add(convertBrandPojoToData(b));
        }
        return list2;
    }

    public void update(int id, BrandForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        BrandPojo p = convertBrandFormToPojo(f);
        normalizeBrand(p);
        brandService.update(id,p);
    }

}
