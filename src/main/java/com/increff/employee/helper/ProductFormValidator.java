package com.increff.employee.helper;

import com.increff.employee.model.form.ProductForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;

public class ProductFormValidator {

    public static void validate(ProductForm p) throws ApiException {
        if(StringUtil.isEmpty(p.getBarcode())){
            throw  new ApiException("Barcode cannot be empty");
        }
        if(StringUtil.isEmpty(p.getName())){
            throw  new ApiException("Name cannot be empty");
        }
        if(StringUtil.isEmpty(p.getBrand())){
            throw  new ApiException("Brand cannot be empty");
        }
        if(StringUtil.isEmpty(p.getCategory())){
            throw  new ApiException("Category cannot be empty");
        }
        if(p.getMrp() == 0.0d){
            throw  new ApiException("Mrp cannot be empty");
        }
        if (p.getMrp() < 0){
            throw new ApiException("Mrp cannot be negative.");
        }

    }
}
