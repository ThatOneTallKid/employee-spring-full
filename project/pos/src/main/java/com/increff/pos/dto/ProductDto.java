package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.pos.model.data.BrandErrorData;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.data.ProductErrorData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertUtil;
import com.increff.pos.util.ErrorUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.pos.helper.InventoryFormHelper.convertInventoryFormToPojo;
import static com.increff.pos.helper.ProductFormHelper.*;


@Component
public class ProductDto {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;


    @Transactional(rollbackOn = ApiException.class)
    public void add(List<ProductForm> forms) throws ApiException, JsonProcessingException {
        List<ProductErrorData> errorData = new ArrayList<>();
        errorData.clear();
        int errorSize = 0;
        for(ProductForm form: forms) {
            ProductErrorData productErrorData= ConvertUtil.convert(form, ProductErrorData.class);
            productErrorData.setMessage("");
            try{
                ValidationUtil.validateForms(form);
                normalizeProduct(form);
                BrandPojo brandPojo = brandService.getBrandByParams(form.getBrand(), form.getCategory());
                productService.checkSame(form.getBarcode());
            }
            catch (ApiException e) {
                errorSize++;
                productErrorData.setMessage(e.getMessage());
            }
            errorData.add(productErrorData);
        }

        if(errorSize > 0){
            ErrorUtil.throwErrors(errorData);
        }
            bulkAdd(forms);
    }

    @Transactional(rollbackOn = ApiException.class)
    private void bulkAdd(List<ProductForm> forms) throws ApiException {
        for(ProductForm form: forms) {
            productService.add(convertProductFormToPojo(form, brandService.getBrandByParams(form.getBrand(),
                    form.getCategory()).getId()));
        }
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        BrandPojo brandPojo= brandService.getCheck(id);
        return convertProductPojoToData(productPojo, brandPojo);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<>();
        for(ProductPojo productPojo : list) {
            BrandPojo brandPojo= brandService.getCheck(productPojo.getBrandCategory());
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
