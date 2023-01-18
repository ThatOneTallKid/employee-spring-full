package com.increff.pos.helper;

import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;

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

    public static void normalizeProduct(ProductForm p) {
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
        p.setName(StringUtil.toLowerCase(p.getName()));
    }
}
