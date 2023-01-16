package com.increff.employee.controller;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderItemApiController {

    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "Adds an Order Item")
    @RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> form) throws ApiException {
        orderItemDto.add(form);
    }

    @ApiOperation(value = "Gets a Order Item by ID")
    @RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
    public OrderItemData get(@PathVariable int id) throws ApiException {
        return orderItemDto.get(id);
    }

    @ApiOperation(value ="Gets all order items")
    @RequestMapping(path = "/api/orderitem", method = RequestMethod.GET)
    public List<OrderItemData> getAll() throws ApiException {
        return orderItemDto.getAll();
    }
    @ApiOperation(value ="Gets all order items")
    @RequestMapping(path = "/api/orderview/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getOrderByID(@PathVariable int id) throws ApiException {
        List<OrderItemData> list = orderItemDto.getOrderByID(id);
        return list;
    }

    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
        orderItemDto.update(id,f);

    }
}
