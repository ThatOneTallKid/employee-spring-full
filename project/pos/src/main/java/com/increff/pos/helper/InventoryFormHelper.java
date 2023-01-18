package com.increff.pos.helper;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;

public class InventoryFormHelper {

    public static InventoryPojo convertInventoryFormToPojo(InventoryForm f, int id) throws ApiException {
        InventoryPojo i = new InventoryPojo();
        i.setQty(f.getQty());
        i.setId(id);
        return i;
    }

    public static InventoryData convertInventoryPojoToData(InventoryPojo i, String barcode, String name) throws ApiException {
        InventoryData d = new InventoryData();
        d.setId(i.getId());
        d.setQty(i.getQty());
        d.setName(name);
        d.setBarcode(barcode);
        return d;
    }
}
