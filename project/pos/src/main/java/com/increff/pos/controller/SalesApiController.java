package com.increff.pos.controller;

import com.increff.pos.dto.SalesDto;
import com.increff.pos.model.form.SalesForm;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/sales")
public class SalesApiController {

    @Autowired
    private SalesDto salesDto;

    @ApiOperation(value = "Gets all the Sales data")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<SalesPojo> getAll() throws ApiException {
        return salesDto.getAll();
    }

    @ApiOperation(value = "get all Sales data between 2 dates")
    @RequestMapping(path = "/filter", method = RequestMethod.POST)
    public List<SalesPojo> getAllByDate(@RequestBody SalesForm salesForm) throws ApiException {
        return salesDto.getAllByDate(salesForm);
    }

    @ApiOperation(value = "Runs the scheduler")
    @RequestMapping(path = "/scheduler", method = RequestMethod.GET)
    public void runScheduler() {
        salesDto.createReport();
    }
}
