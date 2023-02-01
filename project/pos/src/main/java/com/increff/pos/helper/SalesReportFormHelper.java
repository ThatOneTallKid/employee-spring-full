package com.increff.pos.helper;

import com.increff.pos.model.form.ProductForm;
import com.increff.pos.model.form.SalesReportForm;
import com.increff.pos.util.StringUtil;

public class SalesReportFormHelper {
    public static void normalizeSalesReportForm(SalesReportForm salesReportForm) {
        salesReportForm.setBrand(StringUtil.toLowerCase(salesReportForm.getBrand()).trim());
        salesReportForm.setCategory(StringUtil.toLowerCase(salesReportForm.getCategory()).trim());
    }
}
