package com.increff.employee.helper;

import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;

public class ProductFormHelper {

    public static ProductPojo convertProductFormToPojo(ProductForm f, int id) {
        ProductPojo p = new ProductPojo();
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setBrandCategory(id);
        p.setMrp(f.getMrp());
        return p;
    }

    public static ProductData convertProductPojoToData(ProductPojo p, BrandPojo b) {
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBarcode(p.getBarcode());
        d.setBrand(b.getBrand());
        d.setCategory(b.getCategory());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        return d;
    }

    public static void normalizeProduct(ProductPojo p) {
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
        p.setName(StringUtil.toLowerCase(p.getName()));
    }
}
