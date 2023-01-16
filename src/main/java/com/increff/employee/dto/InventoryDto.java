package com.increff.employee.dto;

import com.increff.employee.helper.InventoryFormValidator;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static  com.increff.employee.util.ConvertUtil.*;


import java.util.ArrayList;
import java.util.List;

@Configuration
public class InventoryDto {
    
    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    public void add(InventoryForm f) throws ApiException {
        InventoryFormValidator.validate(f);
        int p_id = productService.getIDByBarcode(f.getBarcode());
        InventoryPojo b = convertInventoryFormToPojo(f, p_id);
        inventoryService.add(b);
    }

    public InventoryData get(int id) throws ApiException{
        InventoryPojo p = inventoryService.get(id);
        String barcode = productService.getBarcodeByID(id);
        return convertInventoryPojoToData(p, barcode);
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<>();
        for(InventoryPojo b : list) {
            String barcode = productService.getBarcodeByID(b.getId());
            list2.add(convertInventoryPojoToData(b, barcode));
        }
        return list2;
    }

    public void update(int id, InventoryForm f) throws ApiException {
        InventoryFormValidator.validate(f);
        int p_id = productService.getIDByBarcode(f.getBarcode());
        InventoryPojo p = convertInventoryFormToPojo(f,p_id);
        inventoryService.update(id,p);
    }
}
