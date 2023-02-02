package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    @Test(expected = ApiException.class)
    public void addTest() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        int expectedBrandCategory = 2;
        String expectedName = "name";
        Double expectedMrp = 23.00;
        String expectedBarcode = "12345678";

        ProductPojo data = productService.get(productService.getIDByBarcode(expectedBarcode));
        assertEquals(expectedBarcode, data.getBarcode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedMrp, data.getMrp(), 0.001);
        assertEquals(expectedBrandCategory, data.getBrandCategory());
        productService.checkSame("12345678");
    }

    @Test
    public void getAllTest() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo productPojo1 = new ProductPojo();
        productPojo1.setBarcode("12345679");
        productPojo1.setName("name");
        productPojo1.setBrandCategory(3);
        productPojo1.setMrp(23.00);
        productService.add(productPojo1);

        List<ProductPojo> list = productService.getAll();

        assertEquals(2, list.size());
    }

    @Test
    public void updateTest() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo productPojo1 = new ProductPojo();
        productPojo1.setBarcode("12345678");
        productPojo1.setName("name2");
        productPojo1.setBrandCategory(2);
        productPojo1.setMrp(27.00);

        int expectedBrandCategory = 2;
        String expectedName = "name2";
        Double expectedMrp = 27.00;
        String expectedBarcode = "12345678";

        ProductPojo pojo = productService.get(productService.getIDByBarcode(expectedBarcode));
        productService.update(pojo.getId(), productPojo1);
        ProductPojo data = productService.get(pojo.getId());
        assertEquals(expectedBarcode, data.getBarcode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedMrp, data.getMrp(), 0.001);
        assertEquals(expectedBrandCategory, data.getBrandCategory());

    }

    @Test(expected = ApiException.class)
    public void checkId() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(brandService.getBrandByParams(brandPojo.getBrand(),brandPojo.getCategory()).getId());
        productPojo.setMrp(23.00);
        productService.add(productPojo);
        productService.add(productPojo);

        int id = productService.getIDByBarcode("23456789");
    }

    @Test(expected = ApiException.class)
    public void CheckIllegal() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(brandService.getBrandByParams(brandPojo.getBrand(),brandPojo.getCategory()).getId());
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo pojo = productService.getCheck(9);
    }


}
