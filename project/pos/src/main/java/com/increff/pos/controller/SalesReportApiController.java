package com.increff.pos.controller;

import com.increff.pos.dto.SalesReportDto;
import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.SalesReportForm;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/salesreport")
public class SalesReportApiController {
    @Autowired
    private SalesReportDto salesReportDto;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "get all the orders")
    @GetMapping(path = "")
    public List<SalesReportData> getAll() throws ApiException {
        return salesReportDto.getAll();
    }

    @ApiOperation(value = "get data within filter")
    @PostMapping(path = "/filter")
    public List<SalesReportData> getFilteredData(@RequestBody SalesReportForm salesReportForm) throws ApiException {
        return salesReportDto.getFilteredData(salesReportForm);
    }
}
