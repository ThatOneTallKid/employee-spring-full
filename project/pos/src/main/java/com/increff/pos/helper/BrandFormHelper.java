package com.increff.pos.helper;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.util.ConvertUtil;
import com.increff.pos.util.StringUtil;

public class BrandFormHelper {
    public static BrandPojo convertBrandFormToPojo(BrandForm f){
        BrandPojo brandPojo = ConvertUtil.convert(f, BrandPojo.class);
        return brandPojo;
    }

    public static BrandData convertBrandPojoToData(BrandPojo brandPojo){
        BrandData brandData = ConvertUtil.convert(brandPojo, BrandData.class);
        return brandData;
    }

    public static void normalizeBrand(BrandForm brandForm) {
        brandForm.setBrand(StringUtil.toLowerCase(brandForm.getBrand()).trim());
        brandForm.setCategory(StringUtil.toLowerCase(brandForm.getCategory()).trim());
    }
}
