package com.increff.employee.dto;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.StringUtil;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.ConvertUtil.*;

@Configuration
public class BrandDto {
    @Autowired
    BrandService brandService;

    public void add(BrandForm f) throws ApiException {
        if (StringUtil.isEmpty(f.getCategory()) || StringUtil.isEmpty((f.getBrand()))) {
            throw  new ApiException("Brand or Category cannot be empty");
        }
        BrandPojo b = convertBrandFormToPojo(f);
        StringUtil.normalizeBrand(b);
        if(ValidationUtil.checkNull(brandService.getBrandPojofromBrandCategory(b.getBrand(), b.getCategory()))){
            brandService.add(b);
        }
        else {
            throw new ApiException("Item already exists !");
        }
    }

    public BrandData get(int id) throws ApiException{
        BrandPojo p = brandService.get(id);
        return convertBrandPojoToData(p);
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
        BrandPojo p = convertBrandFormToPojo(f);
        StringUtil.normalizeBrand(p);
        brandService.update(id,p);
    }

}
