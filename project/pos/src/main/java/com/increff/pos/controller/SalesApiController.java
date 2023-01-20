package com.increff.pos.controller;

import com.increff.pos.dto.SalesDto;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        salesDto.createReport();
        return salesDto.getAll();
    }
}
