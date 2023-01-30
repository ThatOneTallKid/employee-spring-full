package com.increff.pos.helper;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.InventoryItem;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.ConvertUtil;
import javafx.util.Pair;

import javax.persistence.Convert;
import java.util.Map;

public class InventoryFormHelper {

    public static InventoryPojo convertInventoryFormToPojo(InventoryForm inventoryForm, int id) throws ApiException {
        InventoryPojo inventoryPojo = ConvertUtil.convert(inventoryForm, InventoryPojo.class);
        inventoryPojo.setId(id);
        return inventoryPojo;
    }

    public static InventoryData convertInventoryPojoToData(InventoryPojo inventoryPojo, String barcode, String name, BrandPojo brandPojo) throws ApiException {
        InventoryData inventoryData = ConvertUtil.convert(inventoryPojo, InventoryData.class);
        inventoryData.setName(name);
        inventoryData.setBarcode(barcode);
        inventoryData.setBrand(brandPojo.getBrand());
        inventoryData.setCategory(brandPojo.getCategory());
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
