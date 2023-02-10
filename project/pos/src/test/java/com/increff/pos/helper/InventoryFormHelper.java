package com.increff.pos.helper;

import com.increff.pos.model.form.InventoryForm;

public class InventoryFormHelper {

    public static InventoryForm createInventory(String productBarcode, int qty) {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(productBarcode);
        inventoryForm.setQty(qty);
        return inventoryForm;
    }
}
