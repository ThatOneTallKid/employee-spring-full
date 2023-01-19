package com.increff.pos.helper;

import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.util.StringUtil;

public class OrderItemFormHelper {

    public static OrderItemPojo convertOrderItemFormToPojo(OrderItemForm form, int productId) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setSellingPrice(form.getSellingPrice());
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQty(form.getQty());
        return  orderItemPojo;
    }

    public static OrderItemData convertOrderItemPojoToData(OrderItemPojo orderItemPojo, String barcode, String name){
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setProductId(orderItemPojo.getProductId());
        orderItemData.setBarcode(barcode);
        orderItemData.setName(name);
        orderItemData.setQty(orderItemPojo.getQty());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        return orderItemData;
    }

    public static void normalizeOrderItem(OrderItemForm orderItemForm) {
        orderItemForm.setBarcode(StringUtil.toLowerCase(orderItemForm.getBarcode()));
    }
}
