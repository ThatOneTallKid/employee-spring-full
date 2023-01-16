package com.increff.employee.helper;

import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;

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
