package com.increff.employee.helper;

import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;

public class OrderItemFormValidator {



    public static void validate(OrderItemForm f) throws ApiException {
        // validate the form
        if (StringUtil.isEmpty(f.getBarcode())) {
            throw new ApiException("Barcode cannot be empty");
        }
        if (f.getSellingPrice() == 0) {
            throw new ApiException("Selling price cannot be Empty or Zero");
        }
        if (f.getQty() <= 0) {
            throw new ApiException("Quantity cannot be Empty, zero or negative");
        }
    }
}
