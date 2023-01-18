package com.increff.pos.dto;

import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.InventoryFormHelper.convertInventoryFormToPojo;
import static com.increff.pos.helper.InventoryFormHelper.convertInventoryPojoToData;

@Component
public class InventoryDto {
    
    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    public void add(InventoryForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        InventoryPojo b = convertInventoryFormToPojo(f,productService.getIDByBarcode(f.getBarcode()) );
        inventoryService.add(b);
    }

    public InventoryData get(int id) throws ApiException{
        InventoryPojo i = inventoryService.get(id);
        ProductPojo p = productService.get(id);
        return convertInventoryPojoToData(i, p.getBarcode(), p.getName());
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<>();
        for(InventoryPojo b : list) {
            ProductPojo p = productService.get(b.getId());
            list2.add(convertInventoryPojoToData(b,p.getBarcode(), p.getName()));
        }
        return list2;
    }

    public void update(int id, InventoryForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        int p_id = productService.getIDByBarcode(f.getBarcode());
        InventoryPojo p = convertInventoryFormToPojo(f,p_id);
        inventoryService.update(id,p);
    }
}
