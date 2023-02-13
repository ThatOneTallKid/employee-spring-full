package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.InventoryPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryServiceTest extends AbstractUnitTest {
    @Autowired
    InventoryService inventoryService;

    @Test
    public void addTest() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQty(3);
        inventoryService.add(inventoryPojo);
        inventoryService.add(inventoryPojo);

        Integer expectedID = 1;
        Integer expectedQty = 6;

        InventoryPojo pojo = inventoryService.getById(expectedID);
        assertEquals(expectedID, pojo.getId());
        assertEquals(expectedQty, pojo.getQty());

    }

    @Test
    public void getAllTest() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQty(3);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo2 = new InventoryPojo();
        inventoryPojo2.setId(2);
        inventoryPojo2.setQty(3);
        inventoryService.add(inventoryPojo2);

        List<InventoryPojo> list = inventoryService.getAll();
        assertEquals(2, list.size());
    }

    @Test
    public void updateTest() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQty(3);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo2 = new InventoryPojo();
        inventoryPojo2.setId(1);
        inventoryPojo2.setQty(9);

        inventoryService.update(1, inventoryPojo2);
        Integer expectedQty = 9;

        InventoryPojo pojo = inventoryService.getById(1);
        assertEquals(expectedQty, pojo.getQty());

    }

    @Test(expected = ApiException.class)
    public void checkIdTest() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQty(3);
        inventoryService.add(inventoryPojo);
        Integer expextedQty = 3;
        InventoryPojo pojo = inventoryService.getCheckInventory(1);
        assertEquals(expextedQty, pojo.getQty());


        InventoryPojo pojo2 = inventoryService.getCheckInventory(3);


    }

    @Test(expected = ApiException.class)
    public void checkReduceInventory() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQty(3);
        inventoryService.add(inventoryPojo);

        inventoryService.reduceInventory(1, 4);
    }


}
