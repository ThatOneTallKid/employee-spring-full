package com.increff.pos.helper;

import com.increff.pos.model.form.OrderItemForm;

public class OrderFormHelper {
    public static OrderItemForm createOrderItem(String barcode,int qty, double sellingPrice) {
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setQty(qty);
        orderItemForm.setBarcode(barcode);
        orderItemForm.setSellingPrice(sellingPrice);
        return orderItemForm;
    }
}
