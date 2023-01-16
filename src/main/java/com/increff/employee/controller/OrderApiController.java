package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.data.OrderData;
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
public class OrderApiController {

    @Autowired
    private OrderDto orderItem;

    @ApiOperation(value = "Adds an Order Item")
    @RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> form) throws ApiException {
        orderItem.add(form);
    }

    @ApiOperation(value ="Gets all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return orderItem.getAll();
    }

    @ApiOperation(value ="Gets all order items by order ID")
    @RequestMapping(path = "/api/orderview/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getOrderByID(@PathVariable int id) throws ApiException {
        List<OrderItemData> list = orderItem.getOrderByID(id);
        return list;
    }

}
