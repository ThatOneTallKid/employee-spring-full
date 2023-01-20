package com.increff.pos.helper;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.InventoryItem;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import javafx.util.Pair;

import java.util.Map;

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

    public static InventoryItem convertMapToItem(Map.Entry<Pair<String,String>, Integer> mapElement) {
        Pair<String, String> p = mapElement.getKey();
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setBrand(p.getKey());
        inventoryItem.setCategory(p.getValue());
        inventoryItem.setQty(mapElement.getValue());
        return inventoryItem;
    }
}
