package com.increff.employee.controller;

import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class InventoryApiController {
    @Autowired
    private InventoryService svc;

    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        InventoryPojo i = convert(form);
        svc.add(i);
    }

    @ApiOperation(value = "Get a Product by ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id)  throws ApiException {
        InventoryPojo i = svc.get(id);
        return convert(i);
    }

    @ApiOperation(value = "Gett All Products")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public  List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list =svc.getAll();
        List<InventoryData> list2 = new ArrayList<>();
        for(InventoryPojo i : list) {
            list2.add(convert(i));
        }
        return list2;
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        InventoryPojo i = convert(f);
        svc.update(id, i);
    }

    private InventoryPojo convert(InventoryForm f) throws ApiException {
        InventoryPojo i = new InventoryPojo();
        i.setQty(f.getQty());
        i.setId(svc.getIDByBarcode(f.getBarcode()));
        return i;
    }

    private InventoryData convert(InventoryPojo i) throws ApiException {
        InventoryData d = new InventoryData();
        d.setId(i.getId());
        d.setQty(i.getQty());
        d.setBarcode(svc.getBarcodeByID(i.getId()));
        return d;
    }

}
