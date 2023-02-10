package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.BrandFormHelper;
import com.increff.pos.helper.InventoryFormHelper;
import com.increff.pos.helper.OrderFormHelper;
import com.increff.pos.helper.ProductFormHelper;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.*;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SalesReportDtoTest extends AbstractUnitTest {
    @Autowired
    OrderDto orderDto;

    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;

    @Autowired
    SalesDto salesDto;

    @Autowired
    SalesReportDto salesReportDto;

    @Test
    public void getFilterTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = ProductFormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = ProductFormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = InventoryFormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = InventoryFormHelper.createInventory("12345679", 8);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = OrderFormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderDto.add(orderItemFormList);

        OrderItemForm orderItemForm1 = OrderFormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);

        SalesReportForm salesReportForm = new SalesReportForm();
        LocalDate date = LocalDate.now();
        salesReportForm.setStartDate(date.toString());
        salesReportForm.setEndDate(date.toString());
        salesReportForm.setCategory("all");
        salesReportForm.setBrand("all");
        List<SalesReportData> list = salesReportDto.getFilteredData(salesReportForm);
        assertEquals(1, list.size());

    }



    @Test
    public void generateCSV() throws ApiException, IOException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = BrandFormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = ProductFormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = ProductFormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = InventoryFormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = InventoryFormHelper.createInventory("12345679", 8);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = OrderFormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderDto.add(orderItemFormList);

        OrderItemForm orderItemForm1 = OrderFormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);

        SalesReportForm salesReportForm = new SalesReportForm();
        LocalDate date = LocalDate.now();
        salesReportForm.setStartDate(date.toString());
        salesReportForm.setEndDate(date.toString());
        salesReportForm.setCategory("all");
        salesReportForm.setBrand("all");
        List<SalesReportData> list = salesReportDto.getFilteredData(salesReportForm);
        assertEquals(1, list.size());

        MockHttpServletResponse response = new MockHttpServletResponse();
        salesReportDto.generateCsv(response);


    }


}
