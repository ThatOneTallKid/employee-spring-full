package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.BrandFormHelper;
import com.increff.pos.helper.InventoryFormHelper;
import com.increff.pos.helper.OrderFormHelper;
import com.increff.pos.helper.ProductFormHelper;
import com.increff.pos.model.form.*;
import com.increff.pos.pojo.SalesPojo;
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
        salesDto.createReport();
        List<SalesPojo> salesPojoList1 = salesDto.getAll();
        assertEquals(1, salesPojoList1.size());
        Integer count1 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(1, (int)count1);

        OrderItemForm orderItemForm1 = OrderFormHelper.createOrderItem("12345679", 2, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);

        salesDto.createReport();
        List<SalesPojo> salesPojoList2 = salesDto.getAll();
        assertEquals(1, salesPojoList2.size());
        Integer count2 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(2, (int) count2);
    }


    @Test
    public void testGetAllByDate() throws ApiException, JsonProcessingException {
        SalesForm salesForm = new SalesForm();
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
        salesDto.createReport();
        List<SalesPojo> salesPojoList1 = salesDto.getAll();
        assertEquals(1, salesPojoList1.size());
        Integer count1 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(1, (int) count1);

        OrderItemForm orderItemForm1 = OrderFormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);

        salesDto.createReport();
        List<SalesPojo> salesPojoList2 = salesDto.getAll();
        assertEquals(1, salesPojoList2.size());
        Integer count2 = salesPojoList1.get(0).getInvoicedOrderCount();
        assertEquals(2,(int) count2);

        LocalDate date = LocalDate.now();
        salesForm.setStartDate(date.toString());
        salesForm.setEndDate(date.toString());

        List<SalesPojo> list = salesDto.getAllByDate(salesForm);
        assertEquals(1,list.size());
    }

}
