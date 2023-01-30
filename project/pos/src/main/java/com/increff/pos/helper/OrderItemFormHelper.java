package com.increff.pos.helper;

import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.util.ConvertUtil;
import com.increff.pos.util.StringUtil;

public class OrderItemFormHelper {

    public static OrderItemPojo convertOrderItemFormToPojo(OrderItemForm form, int productId) {
        OrderItemPojo orderItemPojo = ConvertUtil.convert(form, OrderItemPojo.class);
        orderItemPojo.setProductId(productId);
        return  orderItemPojo;
    }

    public static OrderItemData convertOrderItemPojoToData(OrderItemPojo orderItemPojo, String barcode, String name){
        OrderItemData orderItemData = ConvertUtil.convert(orderItemPojo, OrderItemData.class);
        orderItemData.setBarcode(barcode);
        orderItemData.setName(name);
        return orderItemData;
    }

    public static void normalizeOrderItem(OrderItemForm orderItemForm) {
        orderItemForm.setBarcode(StringUtil.toLowerCase(orderItemForm.getBarcode()));
    }
}
