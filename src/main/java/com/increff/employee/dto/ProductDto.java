package com.increff.employee.dto;

import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.ConvertUtil.convertProductFormToPojo;
import static com.increff.employee.util.ConvertUtil.convertProductPojoToData;

@Configuration
public class ProductDto {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;



    public void add(ProductForm f) throws ApiException{
        BrandPojo b  = brandService.getBrandPojofromBrandCategory(f.getBrand(),  f.getCategory());
        int b_id = b.getId();
        ProductPojo p = convertProductFormToPojo(f, b_id);
        StringUtil.normalizeProduct(p);
        if(StringUtil.isEmpty(p.getBarcode())){
            throw  new ApiException("Barcode cannot be empty");
        }
        if(p.getBrand_category() == 0){
            throw  new ApiException("Brand_Category cannot be empty");
        }
        if(StringUtil.isEmpty(p.getName())){
            throw  new ApiException("Name cannot be empty");
        }
        if(p.getMrp() == 0.0d){
            throw  new ApiException("Mrp cannot be empty");
        }
        if (p.getMrp() < 0){
            throw new ApiException("Mrp cannot be negative.");
        }
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
            BrandPojo bx= brandService.get(b.getBrand_category());
            list2.add(convertProductPojoToData(b, bx));
        }
        return list2;
    }

    public void update(int id, ProductForm f) throws ApiException {
        ProductPojo p = convertProductFormToPojo(f, id);
        StringUtil.normalizeProduct(p);
        productService.update(id,p);
    }
}
