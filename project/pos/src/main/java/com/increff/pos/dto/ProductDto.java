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



    public void add(ProductForm f) throws ApiException{
        normalizeProduct(f);
        ValidationUtil.validateForms(f);
        // check null (get check brand )
        BrandPojo b  = brandService.getBrandPojofromBrandCategory(f.getBrand(),  f.getCategory());
        if(Objects.isNull(b)) {
            throw new ApiException("Brand and Category not Founc");
        }
        ProductPojo p = convertProductFormToPojo(f, b.getId());
        productService.add(p);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = productService.get(id);
        BrandPojo b= brandService.get(id);
        return convertProductPojoToData(p, b);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<>();
        for(ProductPojo b : list) {
            BrandPojo bx= brandService.get(b.getBrandCategory());
            list2.add(convertProductPojoToData(b, bx));
        }
        return list2;
    }

    public void update(int id, ProductForm f) throws ApiException {
        normalizeProduct(f);
        ValidationUtil.validateForms(f);
        ProductPojo p = convertProductFormToPojo(f, id);
        productService.update(id,p);
    }
}
