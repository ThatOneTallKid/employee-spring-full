package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.helper.InventoryFormHelper;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.InventoryErrorData;
import com.increff.pos.model.data.InventoryItem;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.model.form.InventoryReportForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/inventory")
public class InventoryApiController {
    @Autowired
    private InventoryDto inventoryDto;



    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public List<InventoryErrorData> add(@RequestBody List<InventoryForm> form) throws ApiException {
        return inventoryDto.add(form);
    }

    @ApiOperation(value = "Get a Product by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id)  throws ApiException {
        return inventoryDto.get(id);
    }

    @ApiOperation(value = "Get a Product by Barcode")
    @RequestMapping(path = "/barcode",method = RequestMethod.GET)
    public InventoryData getByBarcode(@RequestParam(value="barcode") String barcode)  throws ApiException {
        return inventoryDto.getByBarcode(barcode);
    }

    @ApiOperation(value = "Get All Products")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public  List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAll();
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        inventoryDto.update(id, f);
    }

    @ApiOperation(value = "Export Product Report to CSV")
    @RequestMapping(path = "/exportcsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException, ApiException {
        inventoryDto.generateCsv(response);
    }

}
