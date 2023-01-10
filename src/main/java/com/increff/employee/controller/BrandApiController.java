package com.increff.employee.controller;

import com.increff.employee.dto.BrandDto;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO "api/branc" on class level
@Api
@RestController
public class BrandApiController {


    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value= "Adds a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        brandDto.add(form);
    }


    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value ="Gets all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandDto.getAll();
    }

    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        brandDto.update(id,f);

    }

}
