package com.increff.employee.util;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

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
        p.setBrand_category(id);
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
        System.out.println("COnversion");
        d.setId(i.getId());
        d.setQty(i.getQty());
        d.setBarcode(barcode);
        return d;
    }




}
