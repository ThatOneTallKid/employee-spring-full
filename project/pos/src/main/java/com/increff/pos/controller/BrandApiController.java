package com.increff.pos.controller;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.BrandReportForm;
import com.increff.pos.model.form.InvoiceForm;
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

import java.util.List;

//TODO "api/brand" on class level
@Api
@RestController
@RequestMapping(path = "/api/brand")
public class BrandApiController {


    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value= "Adds a brand")
    @PostMapping(path = "")
    public void add(@RequestBody BrandForm form) throws ApiException {
        brandDto.add(form);
    }


    @ApiOperation(value = "Gets a brand by ID")
    @GetMapping(path = "/{id}")
    public BrandData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value ="Gets all brands")
    @GetMapping(path = "")
    public List<BrandData> getAll() {
        return brandDto.getAll();
    }

    @ApiOperation(value = "Updates a brand")
    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        brandDto.update(id,f);

    }

    @ApiOperation(value = "Download Invoice")
    @GetMapping(path = "/report")
    public ResponseEntity<byte[]> getPDF() throws Exception{
        return brandDto.getPDF();
    }

}
