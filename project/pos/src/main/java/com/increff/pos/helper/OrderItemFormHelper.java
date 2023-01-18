package com.increff.pos.helper;

import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.util.StringUtil;

public class OrderItemFormHelper {

    public static OrderItemPojo convertOrderItemFormToPojo(OrderItemForm form, int productId) {
        OrderItemPojo o = new OrderItemPojo();
        o.setSellingPrice(form.getSellingPrice());
        o.setProductId(productId);
        o.setQty(form.getQty());
        return  o;
    }

    public static OrderItemData convertOrderItemPojoToData(OrderItemPojo p, String barcode, String name){
        OrderItemData d = new OrderItemData();
        d.setId(p.getId());
        d.setProductId(p.getProductId());
        d.setBarcode(barcode);
        d.setName(name);
        d.setQty(p.getQty());
        d.setOrderId(p.getOrderId());
        d.setSellingPrice(p.getSellingPrice());
        return d;
    }

    public static void normalizeOrderItem(OrderItemForm o) {
        o.setBarcode(StringUtil.toLowerCase(o.getBarcode()));
    }
}
