package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest {
    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;

    @Autowired
    ProductService productService;

    @Test
    public void addProductTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Brand");
        brandForm.setCategory("CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = new ProductForm();
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productForm.setBarcode("12345678");
        productForm.setName("name");
        productForm.setMrp(23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);

        String expectedBrandName = "brand";
        String expectedCategoryName = "category";
        String expectedName = "name";
        Double expectedMrp = 23.00;
        String expectedBarcode = "12345678";

        ProductData data = productDto.get(productService.getIDByBarcode(expectedBarcode));
        assertEquals(expectedBarcode, data.getBarcode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
        assertEquals(expectedMrp, data.getMrp(), 0.001);
    }

    @Test
    public void getAllProductTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Brand");
        brandForm.setCategory("CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = new ProductForm();
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productForm.setBarcode("12345678");
        productForm.setName("name");
        productForm.setMrp(23.00);
        productFormList.add(productForm);


        ProductForm productForm1 = new ProductForm();
        productForm1.setBrand("brand");
        productForm1.setCategory("category");
        productForm1.setBarcode("12345679");
        productForm1.setName("name2");
        productForm1.setMrp(22.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        List<ProductData> list = productDto.getAll();
        assertEquals(2, list.size());
    }

    @Test
    public void updateProductTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Brand");
        brandForm.setCategory("CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = new ProductForm();
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productForm.setBarcode("12345678");
        productForm.setName("name");
        productForm.setMrp(23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);

        ProductForm productForm1 = new ProductForm();
        productForm1.setBrand("brand");
        productForm1.setCategory("category");
        productForm1.setBarcode("12345678");
        productForm1.setName("name");
        productForm1.setMrp(27.00);

        String expectedBrandName = "brand";
        String expectedCategoryName = "category";
        String expectedName = "name";
        Double expectedMrp = 27.00;
        String expectedBarcode = "12345678";

        productDto.update(productService.getIDByBarcode(expectedBarcode), productForm1);

        ProductData data = productDto.get(productService.getIDByBarcode(expectedBarcode));
        assertEquals(expectedBarcode, data.getBarcode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
        assertEquals(expectedMrp, data.getMrp(), 0.001);

    }

    @Test(expected = ApiException.class)
    public void addDuplicate() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Brand");
        brandForm.setCategory("CateGory");
        brandFormList.add(brandForm);


        brandDto.add(brandFormList);

        ProductForm productForm = new ProductForm();
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productForm.setBarcode("12345678");
        productForm.setName("name");
        productForm.setMrp(23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);
        productDto.add(productFormList);
    }

    @Test(expected = ApiException.class)
    public void addIllegal() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Brand");
        brandForm.setCategory("CateGory");
        brandFormList.add(brandForm);


        brandDto.add(brandFormList);

        ProductForm productForm = new ProductForm();
        productForm.setBrand("brand2");
        productForm.setCategory("category3");
        productForm.setBarcode("12345678");
        productForm.setName("name");
        productForm.setMrp(23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);
    }

}
