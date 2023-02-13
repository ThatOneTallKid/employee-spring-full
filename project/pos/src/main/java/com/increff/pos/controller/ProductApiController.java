package com.increff.pos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {

    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody List<ProductForm> form) throws ApiException, JsonProcessingException {
        productDto.add(form);
    }

    @ApiOperation(value = "Gets a Product by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable Integer id) throws ApiException {
        return productDto.get(id);
    }

    @ApiOperation(value ="Gets all Products")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return productDto.getAll();
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody ProductForm f) throws ApiException {
        productDto.update(id, f);
    }
    

}
