package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItem;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

        OrderItemForm orderItemForm1 = new OrderItemForm();
        orderItemForm1.setBarcode("12345679");
        orderItemForm1.setQty(3);
        orderItemForm1.setSellingPrice(39.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);

        List<OrderData> list = orderDto.getAll();
        assertEquals(2, list.size());

    }

    @Test(expected = ApiException.class)
    public void checkDuplicates() throws JsonProcessingException, ApiException {
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

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode("12345678");
        orderItemForm.setQty(2);
        orderItemForm.setSellingPrice(34.00);
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

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode("12345678");
        orderItemForm.setQty(10);
        orderItemForm.setSellingPrice(34.00);
        orderItemFormList.add(orderItemForm);

        orderDto.add(orderItemFormList);

    }


    @Test
    public void getOrderByOrderID() throws JsonProcessingException, ApiException {
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

        OrderItemForm orderItemForm1 = new OrderItemForm();
        orderItemForm1.setBarcode("12345679");
        orderItemForm1.setQty(3);
        orderItemForm1.setSellingPrice(39.00);
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

        OrderItemForm orderItemForm1 = new OrderItemForm();
        orderItemForm1.setBarcode("12345679");
        orderItemForm1.setQty(3);
        orderItemForm1.setSellingPrice(39.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.add(orderItemFormList);
        List<OrderData> list = orderDto.getAll();

        ResponseEntity<byte[]> response= orderDto.getPDF(list.get(0).getId(), "http://localhost:8085/fop/api/invoice");
        assertNotEquals(null, response);
    }

}
