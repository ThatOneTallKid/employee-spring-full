package com.increff.employee.helper;

import com.increff.employee.model.form.BrandForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;

public class BrandFormValidator {

    public static void validate(BrandForm f) throws ApiException {
        if (StringUtil.isEmpty(f.getCategory()) || StringUtil.isEmpty((f.getBrand()))) {
            throw  new ApiException("Brand or Category cannot be empty");
        }
    }
}
