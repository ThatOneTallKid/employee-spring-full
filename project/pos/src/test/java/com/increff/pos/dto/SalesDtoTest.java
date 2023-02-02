package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.model.form.*;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.SalesService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SalesDtoTest extends AbstractUnitTest {
    @Autowired
    SalesDto salesDto;

    @Autowired
    OrderDto orderDto;

    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;

    @Autowired
    SalesService salesService;

    @Test
    public void testReport() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
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
        inventoryForm.setQty(7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = new InventoryForm();
        form.setBarcode("12345679");
        form.setQty(8);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode("12345678");
        orderItemForm.setQty(2);
        orderItemForm.setSellingPrice(34.00);
        orderItemFormList.add(orderItemForm);
        orderDto.add(orderItemFormList);
        salesDto.createReport();
        List<SalesPojo> salesPojoList1 = salesDto.getAll();
        assertEquals(1, salesPojoList1.size());
        int count1 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(1, count1);

        OrderItemForm orderItemForm1 = new OrderItemForm();
        orderItemForm1.setBarcode("12345679");
        orderItemForm1.setQty(3);
        orderItemForm1.setSellingPrice(39.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);

        salesDto.createReport();
        List<SalesPojo> salesPojoList2 = salesDto.getAll();
        assertEquals(1, salesPojoList2.size());
        int count2 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(2, count2);
    }


    @Test
    public void testGetAllByDate() throws ApiException, JsonProcessingException {
        SalesForm salesForm = new SalesForm();
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
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
        inventoryForm.setQty(7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = new InventoryForm();
        form.setBarcode("12345679");
        form.setQty(8);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode("12345678");
        orderItemForm.setQty(2);
        orderItemForm.setSellingPrice(34.00);
        orderItemFormList.add(orderItemForm);
        orderDto.add(orderItemFormList);
        salesDto.createReport();
        List<SalesPojo> salesPojoList1 = salesDto.getAll();
        assertEquals(1, salesPojoList1.size());
        int count1 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(1, count1);

        OrderItemForm orderItemForm1 = new OrderItemForm();
        orderItemForm1.setBarcode("12345679");
        orderItemForm1.setQty(3);
        orderItemForm1.setSellingPrice(39.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);

        salesDto.createReport();
        List<SalesPojo> salesPojoList2 = salesDto.getAll();
        assertEquals(1, salesPojoList2.size());
        int count2 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(2, count2);

        LocalDate date = LocalDate.now();
        salesForm.setStartDate(date.toString());
        salesForm.setEndDate(date.toString());

        List<SalesPojo> list = salesDto.getAllByDate(salesForm);
        assertEquals(1,list.size());
    }

}
