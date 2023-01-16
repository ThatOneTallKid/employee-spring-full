package com.increff.employee.util;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.data.*;
import com.increff.employee.model.form.*;
import com.increff.employee.pojo.*;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ConvertUtil {

    @Autowired
    private static InventoryService inventoryService;

    @Autowired
    private static ProductDao productDao;


    public static BrandPojo convertBrandFormToPojo(BrandForm f){
        BrandPojo b = new BrandPojo();
        b.setBrand(f.getBrand());
        b.setCategory(f.getCategory());
        return b;
    }

    public static BrandData convertBrandPojoToData(BrandPojo b){
        BrandData d = new BrandData();
        d.setId(b.getId());
        d.setBrand(b.getBrand());
        d.setCategory(b.getCategory());
        return d;
    }

    public static ProductPojo convertProductFormToPojo(ProductForm f, int id) {
        ProductPojo p = new ProductPojo();
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setBrandCategory(id);
        p.setMrp(f.getMrp());
        return p;
    }

    public static ProductData convertProductPojoToData(ProductPojo p, BrandPojo b) {
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBarcode(p.getBarcode());
        d.setBrand(b.getBrand());
        d.setCategory(b.getCategory());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        return d;
    }

    public static InventoryPojo convertInventoryFormToPojo(InventoryForm f, int id) throws ApiException {
        InventoryPojo i = new InventoryPojo();
        i.setQty(f.getQty());
        i.setId(id);
        return i;
    }

    public static InventoryData convertInventoryPojoToData(InventoryPojo i, String barcode) throws ApiException {
        InventoryData d = new InventoryData();
        d.setId(i.getId());
        d.setQty(i.getQty());
        d.setBarcode(barcode);
        return d;
    }

    public static OrderItemPojo convertOrderItemFormToPojo(OrderItemForm form, int productId) {
        OrderItemPojo o = new OrderItemPojo();
        o.setSellingPrice(form.getSellingPrice());
        o.setProductId(productId);
        o.setQty(form.getQty());
        return  o;
    }

    public static OrderItemData convertOrderItemPojoToData(OrderItemPojo p, String barcode){
        OrderItemData d = new OrderItemData();
        d.setId(p.getId());
        d.setProductId(p.getProductId());
        d.setBarcode(barcode);
        d.setQty(p.getQty());
        d.setOrderId(p.getOrderId());
        d.setSellingPrice(p.getSellingPrice());
        return d;
    }

    public static OrderPojo convertOrderFormToPojo(OrderForm f) {
        OrderPojo o = new OrderPojo();
        return  o;
    }

    public static OrderData convertOrderPojoToData(OrderPojo p) {
        OrderData d = new OrderData();
        d.setId(p.getId());
        Timestamp timestamp = p.getCreatedAt();
        d.setOrderDate(LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.ofHoursMinutes(5, 30)));
        return d;
    }

}
