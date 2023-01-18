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

    @ApiOperation(value = "Download Invoice")
    @RequestMapping(path = "/api/brandreport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF() throws Exception{
//        System.out.println("here");
//        List<BrandData> brandItems= getAll();
//        BrandReportForm brandReportForm = new BrandReportForm();
//        brandReportForm.setBrandItems(brandItems);
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8085/fop/api/brandreport";
//        byte[] contents = restTemplate.postForEntity(url, brandReportForm, byte[].class).getBody();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        String filename = "brandreport.pdf";
//        headers.setContentDispositionFormData(filename, filename);
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
//        return response;
        return brandDto.getPDF();
    }

}
