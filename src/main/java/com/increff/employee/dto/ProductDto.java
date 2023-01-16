package com.increff.employee.dto;

import com.increff.employee.helper.ProductFormHelper;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.employee.helper.ProductFormHelper.*;


@Configuration
public class ProductDto {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;



    public void add(ProductForm f) throws ApiException{
        ValidationUtil.validateForms(f);
        // check null (get check brand )
        BrandPojo b  = brandService.getBrandPojofromBrandCategory(f.getBrand(),  f.getCategory());
        if(Objects.isNull(b)) {
            throw new ApiException("Brand and Category not Founc");
        }
        ProductPojo p = convertProductFormToPojo(f, b.getId());
        normalizeProduct(p);
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
        ValidationUtil.validateForms(f);
        ProductPojo p = convertProductFormToPojo(f, id);
        normalizeProduct(p);
        productService.update(id,p);
    }
}
