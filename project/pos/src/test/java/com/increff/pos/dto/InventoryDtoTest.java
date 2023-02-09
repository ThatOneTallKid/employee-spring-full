package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.InventoryItem;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryDtoTest extends AbstractUnitTest {
    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;


    @Test
    public void addInventoryTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
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
        List<ProductPojo> productPojoList = productService.getAll();
        assertEquals(1, productPojoList.size());
        ProductPojo productPojo = productPojoList.get(0);
        assertEquals(1, productPojo.getId());
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("12345678");
        inventoryForm.setQty(3);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();



        String expectedBrandName = "brand";
        String expectedCategoryName = "category";
        String expectedName = "name";
        String expectedBarcode = "12345678";
        int expectedQty = 3;

        InventoryData data = inventoryDto.getByBarcode("12345678");
        assertEquals(expectedBarcode, data.getBarcode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
        assertEquals(expectedQty, data.getQty());
    }

    @Test
    public void getAllTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
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
        productForm1.setName("name1");
        productForm1.setMrp(28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("12345678");
        inventoryForm.setQty(3);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = new InventoryForm();
        form.setBarcode("12345679");
        form.setQty(3);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        List<InventoryData> list = inventoryDto.getAll();

        assertEquals(2, list.size());
    }

    @Test
    public void updateTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
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

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("12345678");
        inventoryForm.setQty(3);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        InventoryForm inventoryForm1 = new InventoryForm();
        inventoryForm1.setBarcode("12345678");
        inventoryForm1.setQty(6);
        inventoryFormList.add(inventoryForm1);

        String expectedBarcode = "12345678";
        int expectedQty = 6;

        InventoryData inventoryData = inventoryDto.get(inventoryDto.getByBarcode(expectedBarcode).getId());
        inventoryDto.update(inventoryData.getId(), inventoryForm1);

        InventoryPojo pojo = inventoryService.getById(inventoryData.getId());
        assertEquals(expectedQty, pojo.getQty());

    }

    @Test
    public void testCSV() throws IOException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
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
        productForm1.setName("name1");
        productForm1.setMrp(28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("12345678");
        inventoryForm.setQty(3);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = new InventoryForm();
        form.setBarcode("12345679");
        form.setQty(3);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);
        MockHttpServletResponse response = new MockHttpServletResponse();
        inventoryDto.generateCsv(response);

        List<InventoryItem> list= inventoryDto.getAllItem();
        assertEquals(1, list.size());

    }

    @Test(expected = ApiException.class)
    public void addIllegal() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
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

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("12345678");
        inventoryForm.setQty(3);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = new InventoryForm();
        form.setBarcode("12345679");
        form.setQty(3);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

    }

    @Test
    public void testGetByBarcode() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
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

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("12345678");
        inventoryForm.setQty(3);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        String expectedBarcode = "12345678";
        InventoryData inventoryData = inventoryDto.get(inventoryDto.getByBarcode(expectedBarcode).getId());
        assertEquals(expectedBarcode, inventoryData.getBarcode());
    }



}
