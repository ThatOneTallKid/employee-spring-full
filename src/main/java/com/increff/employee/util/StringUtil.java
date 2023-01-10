package com.increff.employee.util;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;

public class StringUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String toLowerCase(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

    public static void normalizeBrand(BrandPojo b) {
        b.setBrand(StringUtil.toLowerCase(b.getBrand()));
        b.setCategory(StringUtil.toLowerCase(b.getCategory()));
    }

    public static void normalizeProduct(ProductPojo p) {
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

}