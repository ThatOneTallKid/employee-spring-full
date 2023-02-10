package com.increff.pos.helper;

import com.increff.pos.model.form.ProductForm;

public class ProductFormHelper {

    public static ProductForm createProduct(String productBarcode, String productName, String brandName, String category, double productMrp) {
        ProductForm productForm = new ProductForm();
        productForm.setBrand(brandName);
        productForm.setCategory(category);
        productForm.setName(productName);
        productForm.setBarcode(productBarcode);
        productForm.setMrp(productMrp);
        return productForm;
    }
}
