package com.increff.pos.helper;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.util.StringUtil;

public class BrandFormHelper {
    public static BrandPojo convertBrandFormToPojo(BrandForm f){
        BrandPojo b = new BrandPojo();
        b.setBrand(f.getBrand());
        b.setCategory(f.getCategory());
        return b;
    }

    public static BrandData convertBrandPojoToData(BrandPojo b){
        BrandData d = new BrandData();
        d.setId(b.getId());
        d.setBrand(b.getBrand());
        d.setCategory(b.getCategory());
        return d;
    }

    public static void normalizeBrand(BrandPojo b) {
        b.setBrand(StringUtil.toLowerCase(b.getBrand()));
        b.setCategory(StringUtil.toLowerCase(b.getCategory()));
    }
}
