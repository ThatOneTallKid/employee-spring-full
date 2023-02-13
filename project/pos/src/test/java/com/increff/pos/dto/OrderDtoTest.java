package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.BrandFormHelper;
import com.increff.pos.helper.InventoryFormHelper;
import com.increff.pos.helper.OrderFormHelper;
import com.increff.pos.helper.ProductFormHelper;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderDtoTest extends AbstractUnitTest {
    @Autowired
    OrderDto orderDto;

    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;


    @Test
    public void addOrderTest() throws JsonProcessingException, ApiException {
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

        List<OrderData> list = orderDto.getAll();
        assertEquals(2, list.size());
        List<InventoryData> inventoryDataList = inventoryDto.getAll();
        assertEquals(2, inventoryDataList.size());
        assertEquals(3, (int) inventoryDataList.get(0).getQty());
        assertEquals(5, (int) inventoryDataList.get(1).getQty());


    }

    @Test(expected = ApiException.class)
    public void checkDuplicates() throws JsonProcessingException, ApiException {
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

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = OrderFormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderItemFormList.add(orderItemForm);
        orderDto.add(orderItemFormList);

    }

    @Test(expected = ApiException.class)
    public void wrongInventory() throws JsonProcessingException, ApiException {
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

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = OrderFormHelper.createOrderItem("12345678", 8, 23.00);
        orderItemFormList.add(orderItemForm);

        orderDto.add(orderItemFormList);

    }


    @Test
    public void getOrderByOrderID() throws JsonProcessingException, ApiException {
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

        List<OrderData> list = orderDto.getAll();
        List<OrderItemData> list1 = orderDto.getOrderItemsByID(list.get(0).getId());
        List<OrderItemData> list2 = orderDto.getOrderItemsByID(list.get(1).getId());

        assertEquals(2, list.size());
        assertEquals(1, list1.size());
        assertEquals(2, list2.size());

    }

    @Test
    public void getPDF() throws Exception {
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

        InventoryForm inventoryForm =  InventoryFormHelper.createInventory("12345678", 7);
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
        List<OrderData> list = orderDto.getAll();

        ResponseEntity<byte[]> response= orderDto.getPDF(list.get(0).getId(), "http://localhost:8085/fop/api/invoice");
        assertNotEquals(null, response);
    }



}
