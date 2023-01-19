package com.increff.pos.helper;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.InventoryItem;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;

public class InventoryFormHelper {

    public static InventoryPojo convertInventoryFormToPojo(InventoryForm inventoryForm, int id) throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQty(inventoryForm.getQty());
        inventoryPojo.setId(id);
        return inventoryPojo;
    }

    public static InventoryData convertInventoryPojoToData(InventoryPojo inventoryPojo, String barcode, String name) throws ApiException {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setQty(inventoryPojo.getQty());
        inventoryData.setName(name);
        inventoryData.setBarcode(barcode);
        return inventoryData;
    }

    public static InventoryItem convertInventoryDataToItem(InventoryData inventoryData, String brand, String category) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(inventoryData.getId());
        inventoryItem.setQty(inventoryData.getQty());
        inventoryItem.setName(inventoryData.getName());
        inventoryItem.setBrand(brand);
        inventoryItem.setCategory(category);
        return inventoryItem;
    }
}
