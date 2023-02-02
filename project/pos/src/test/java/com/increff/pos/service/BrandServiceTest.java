package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandServiceTest extends AbstractUnitTest {
    @Autowired
    BrandService brandService;

    @Test
    public void addTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);


        String expectedBrand = "brand";
        String expectedCategory = "category";

        BrandPojo brandPojo1 = brandService.getBrandByParams(expectedBrand, expectedCategory);
        assertEquals(expectedBrand, brandPojo1.getBrand());
        assertEquals(expectedCategory, brandPojo1.getCategory());
    }

    @Test
    public void getAllTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);


        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("brand1");
        brandPojo1.setCategory("category1");
        brandService.add(brandPojo1);


        List<BrandPojo> list = brandService.getAll();
        assertEquals(2, list.size());
    }

    @Test(expected = ApiException.class)
    public void checkIllegalId() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        BrandPojo pojo = brandService.getBrandByParams("brand", "category");
        BrandPojo pojo2 = brandService.getCheck(3);
    }

    @Test(expected = ApiException.class)
    public void updateTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("brand1");
        brandPojo1.setCategory("category1");

        String expectedBrand = "brand1";
        String expectedCategory = "category1";

        BrandPojo pojo = brandService.getBrandByParams("brand", "category");
        brandService.update(pojo.getId(), brandPojo1);
        BrandPojo pojo1 = brandService.getCheck(pojo.getId());
        assertEquals(expectedBrand, pojo1.getBrand());
        assertEquals(expectedCategory, pojo1.getCategory());
        // throws exception if same brand and category exists
        brandService.checkBrandExists("brand1", "category1");
    }

    @Test(expected = ApiException.class)
    public void checkIllegal() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        BrandPojo pojo = brandService.getBrandByParams("brand", "category");
        // illegal
        BrandPojo pojo1 = brandService.getBrandByParams("brand1", "category1");
    }

    @Test(expected = Exception.class)
    public void checkDuplicateBrandExists() throws Exception {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("brand");
        brandPojo1.setCategory("category");
        brandService.add(brandPojo1);
    }




}
