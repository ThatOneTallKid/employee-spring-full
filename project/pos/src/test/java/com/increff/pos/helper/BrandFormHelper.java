package com.increff.pos.helper;

import com.increff.pos.model.form.BrandForm;

public class BrandFormHelper {

    public static BrandForm createBrand(String brandName, String category) {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brandName);
        brandForm.setCategory(category);
        return brandForm;
    }
}
