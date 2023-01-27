package com.increff.pos.helper;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.util.StringUtil;

public class BrandFormHelper {
    public static BrandPojo convertBrandFormToPojo(BrandForm f){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(f.getBrand());
        brandPojo.setCategory(f.getCategory());
        return brandPojo;
    }

    //TODO create generic convert fucntion
    public static BrandData convertBrandPojoToData(BrandPojo brandPojo){
        BrandData brandData = new BrandData();
        brandData.setId(brandPojo.getId());
        brandData.setBrand(brandPojo.getBrand());
        brandData.setCategory(brandPojo.getCategory());
        return brandData;
    }

    public static void normalizeBrand(BrandForm brandForm) {
        brandForm.setBrand(StringUtil.toLowerCase(brandForm.getBrand()));
        brandForm.setCategory(StringUtil.toLowerCase(brandForm.getCategory()));
    }
}
