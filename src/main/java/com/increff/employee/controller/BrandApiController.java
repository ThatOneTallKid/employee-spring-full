package com.increff.employee.controller;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//TODO "api/branc" on class level
@Api
@RestController
public class BrandApiController {

    @Autowired
    private BrandService svc;

    @ApiOperation(value= "Adds a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        BrandPojo b = convert(form);
        svc.add(b);
    }


    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        BrandPojo  b= svc.get(id);
        return convert(b);
    }

    @ApiOperation(value ="Gets all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        List<BrandPojo> list = svc.getAll();
        List<BrandData> list2 = new ArrayList<>();
        for(BrandPojo b : list) {
            list2.add(convert(b));
        }
        return list2;
    }

    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        BrandPojo b = convert(f);
        svc.update(id, b);

    }

    private static BrandData convert(BrandPojo b) {
        BrandData d = new BrandData();
        d.setId(b.getId());
        d.setBrand(b.getBrand());
        d.setCategory(b.getCategory());
        return d;
    }

    private static BrandPojo convert(BrandForm f) {
        BrandPojo b = new BrandPojo();
        b.setBrand(f.getBrand());
        b.setCategory(f.getCategory());
        return b;
    }
}
