package com.increff.employee.controller;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryApiController {
    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        inventoryDto.add(form);
    }

    @ApiOperation(value = "Get a Product by ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id)  throws ApiException {
        return inventoryDto.get(id);
    }

    @ApiOperation(value = "Get All Products")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public  List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAll();
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        inventoryDto.update(id, f);
    }


}
