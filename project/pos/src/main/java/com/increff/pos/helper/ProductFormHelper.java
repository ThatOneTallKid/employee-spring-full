package com.increff.pos.helper;

import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.ConvertUtil;
import com.increff.pos.util.StringUtil;

public class ProductFormHelper {

    public static ProductPojo convertProductFormToPojo(ProductForm productForm, int id) {
        ProductPojo productPojo = ConvertUtil.convert(productForm, ProductPojo.class);
        productPojo.setBrandCategory(id);
        return productPojo;
    }

    public static ProductData convertProductPojoToData(ProductPojo productPojo, BrandPojo brandPojo) {
        ProductData productData = ConvertUtil.convert(productPojo, ProductData.class);
        productData.setBrand(brandPojo.getBrand());
        productData.setCategory(brandPojo.getCategory());
        return productData;
    }

    public static void normalizeProduct(ProductForm productForm) {
        productForm.setBarcode(StringUtil.toLowerCase(productForm.getBarcode()).trim());
        productForm.setName(StringUtil.toLowerCase(productForm.getName()).trim());
    }
}
