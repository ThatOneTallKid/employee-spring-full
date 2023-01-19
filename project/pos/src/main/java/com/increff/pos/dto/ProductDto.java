package com.increff.pos.dto;

import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.pos.helper.ProductFormHelper.*;


@Component
public class ProductDto {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;



    public void add(ProductForm form) throws ApiException{
        ValidationUtil.validateForms(form);
        normalizeProduct(form);
        BrandPojo brandPojo  = brandService.getBrandPojofromBrandCategory(form.getBrand(),  form.getCategory());
        if(Objects.isNull(brandPojo)) {
            throw new ApiException("Brand and Category not Found");
        }
        ProductPojo productPojo = convertProductFormToPojo(form, brandPojo.getId());
        productService.add(productPojo);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        BrandPojo brandPojo= brandService.get(id);
        return convertProductPojoToData(productPojo, brandPojo);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<>();
        for(ProductPojo productPojo : list) {
            BrandPojo brandPojo= brandService.get(productPojo.getBrandCategory());
            list2.add(convertProductPojoToData(productPojo, brandPojo));
        }
        return list2;
    }

    public void update(int id, ProductForm form) throws ApiException {
        ValidationUtil.validateForms(form);
        normalizeProduct(form);
        ProductPojo productPojo = convertProductFormToPojo(form, id);
        productService.update(id,productPojo);
    }
}
