package com.increff.pos.helper;

import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;

public class ProductFormHelper {

    public static ProductPojo convertProductFormToPojo(ProductForm productForm, int id) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(productForm.getName());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setBrandCategory(id);
        productPojo.setMrp(productForm.getMrp());
        return productPojo;
    }

    public static ProductData convertProductPojoToData(ProductPojo productPojo, BrandPojo brandPojo) {
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(brandPojo.getBrand());
        productData.setCategory(brandPojo.getCategory());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
        return productData;
    }

    public static void normalizeProduct(ProductForm productForm) {
        productForm.setBarcode(StringUtil.toLowerCase(productForm.getBarcode()));
        productForm.setName(StringUtil.toLowerCase(productForm.getName()));
    }
}
