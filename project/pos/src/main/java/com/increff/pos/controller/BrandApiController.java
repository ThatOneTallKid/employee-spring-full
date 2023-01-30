package com.increff.pos.controller;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.data.BrandErrorData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.BrandReportForm;
import com.increff.pos.model.form.InvoiceForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/brand")
public class BrandApiController {

    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value= "Adds a brand")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public List<BrandErrorData> add(@RequestBody List<BrandForm> forms) throws ApiException {
        return brandDto.add(forms);
    }


    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value ="Gets all brands")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandDto.getAll();
    }

    @ApiOperation(value = "Updates a brand")
    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody BrandForm brandForm) throws ApiException {
        brandDto.update(id,brandForm);

    }


    @ApiOperation(value = "Exports to CSV")
    @RequestMapping(path = "/exportcsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException {
        brandDto.generateCsv(response);
    }

}
