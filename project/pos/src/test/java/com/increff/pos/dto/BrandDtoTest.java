package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.BrandFormHelper;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    BrandDto brandDto;

    @Autowired
    BrandService brandService;


    @Test
    public void addBrandTest() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);


        String expectedBrand = "brand";
        String expectedCategory = "category";

        BrandPojo brandPojo = brandService.getBrandByParams(expectedBrand, expectedCategory);
        assertEquals(expectedBrand, brandPojo.getBrand());
        assertEquals(expectedCategory, brandPojo.getCategory());
    }

    @Test
    public void getAllBrandTest() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);


        BrandForm brandForm2 = BrandFormHelper.createBrand("Brand2", "CateGory2");
        brandFormList.add(brandForm2);

        brandDto.add(brandFormList);

        List<BrandData> list = brandDto.getAll();
        assertEquals(2, list.size());
    }

    @Test
    public void updateBrandTest() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        String expectedBrand = "brand";
        String expectedCategory = "category";

        BrandPojo brandPojo =brandService.getBrandByParams(expectedBrand,expectedCategory);

        String newBrand = "brand2";
        String newCategory = "catogory2";
        brandForm.setBrand(newBrand);
        brandForm.setCategory(newCategory);
        brandDto.update(brandPojo.getId(),brandForm);

        BrandPojo pojo = brandService.getCheck(brandPojo.getId());
        assertEquals(newBrand, pojo.getBrand());
        assertEquals(newCategory, pojo.getCategory());
    }

    @Test
    public void getCheck() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        String expectedBrand = "brand";
        String expectedCategory = "category";

        BrandPojo brandPojo =brandService.getBrandByParams(expectedBrand,expectedCategory);

        BrandData brandData = brandDto.get(brandPojo.getId());
        assertEquals(brandData.getBrand(), brandPojo.getBrand());
        assertEquals(brandData.getCategory(), brandPojo.getCategory());
    }

    @Test
    public void testCSV() throws IOException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);
        MockHttpServletResponse response = new MockHttpServletResponse();
        brandDto.generateCsv(response);
        assertEquals("text/csv", response.getContentType());
    }

    @Test(expected = ApiException.class)
    public void addDuplicate() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);
        brandDto.add(brandFormList);
    }

    @Test(expected = ApiException.class)
    public void emptyBrand() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);
    }
}
