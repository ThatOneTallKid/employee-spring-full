package com.increff.pos.controller;

import com.increff.pos.dto.SalesDto;
import com.increff.pos.model.form.SalesForm;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/sales")
public class SalesApiController {

    @Autowired
    private SalesDto salesDto;

    @ApiOperation(value = "Gets all the Sales data")
    @GetMapping(path = "")
    public List<SalesPojo> getAll() throws ApiException {
        return salesDto.getAll();
    }

    @ApiOperation(value = "get all sales date between 2 dates")
    @PostMapping(path = "/filter")
    public List<SalesPojo> getAllByDate(@RequestBody SalesForm salesForm){
        return salesDto.getAllByDate(salesForm);
    }

    @ApiOperation(value = "Runs the scheduler")
    @GetMapping(path = "/scheduler")
    public void runScheduler() throws ApiException {
        salesDto.createReport();
    }
}
