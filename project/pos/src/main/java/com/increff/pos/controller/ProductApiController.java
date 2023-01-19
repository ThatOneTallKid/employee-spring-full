package com.increff.pos.controller;

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
    @PostMapping(path = "")
    public void add(@RequestBody ProductForm form) throws ApiException {
        productDto.add(form);
    }

    @ApiOperation(value = "Gets a Product by ID")
    @GetMapping(path = "/{id}")
    public ProductData get(@PathVariable int id) throws ApiException {
        return productDto.get(id);
    }

    @ApiOperation(value ="Gets all Products")
    @GetMapping(path = "")
    public List<ProductData> getAll() throws ApiException {
        return productDto.getAll();
    }

    @ApiOperation(value = "Updates a Product")
    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
        productDto.update(id, f);
    }
    

}
