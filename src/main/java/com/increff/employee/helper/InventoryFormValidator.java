package com.increff.employee.helper;

import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.service.ApiException;

public class InventoryFormValidator {

    public static void validate(InventoryForm f) throws ApiException {
        if(f.getQty() <= 0){
            throw new ApiException("Quantity cannot be Empty or Negative");
        }
    }
}
