package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.helper.InventoryFormHelper;
import com.increff.pos.model.data.InventoryData;
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
    @PostMapping(path = "")
    public void add(@RequestBody InventoryForm form) throws ApiException {
        inventoryDto.add(form);
    }

    @ApiOperation(value = "Get a Product by ID")
    @GetMapping(path = "/{id}")
    public InventoryData get(@PathVariable int id)  throws ApiException {
        return inventoryDto.get(id);
    }

    @ApiOperation(value = "Get All Products")
    @GetMapping(path = "")
    public  List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAll();
    }

    @ApiOperation(value = "Updates a Product")
    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        inventoryDto.update(id, f);
    }

    @ApiOperation(value = "Generate Inventory Report")
    @GetMapping(path = "/report")
    public ResponseEntity<byte[]> getPDF() throws IOException, ApiException {
        return inventoryDto.getPDF();
    }

}
