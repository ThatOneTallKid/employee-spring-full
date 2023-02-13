package com.increff.pos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/inventory")
public class InventoryApiController {
    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds an Inventory")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody List<InventoryForm> form) throws ApiException, JsonProcessingException {
        inventoryDto.add(form);
    }

    @ApiOperation(value = "Get an Inventory by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable Integer id)  throws ApiException {
        return inventoryDto.get(id);
    }

    @ApiOperation(value = "Get an Inventory by Barcode")
    @RequestMapping(path = "/barcode",method = RequestMethod.GET)
    public InventoryData getByBarcode(@RequestParam(value="barcode") String barcode)  throws ApiException {
        return inventoryDto.getByBarcode(barcode);
    }

    @ApiOperation(value = "Get all Products in Inventory")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public  List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAll();
    }

    @ApiOperation(value = "Updates an Inventory")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody InventoryForm f) throws ApiException {
        inventoryDto.update(id, f);
    }

    @ApiOperation(value = "Export Inventory Report to CSV")
    @RequestMapping(path = "/exportcsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException, ApiException {
        inventoryDto.generateCsv(response);
    }

}
