package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.invoice.InvoiceGenerator;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/order")
public class OrderApiController {

    @Autowired
    private OrderDto orderDto;

    @Autowired
    InvoiceGenerator invoiceGenerator;

    @ApiOperation(value = "Adds an Order Item")
    @PostMapping(path = "/item")
    public void add(@RequestBody List<OrderItemForm> form) throws ApiException {
        orderDto.add(form);
    }

    @ApiOperation(value ="Gets all orders")
    @GetMapping(path = "")
    public List<OrderData> getAll() throws ApiException {
        return orderDto.getAll();
    }

    @ApiOperation(value ="Gets all order items by order ID")
    @GetMapping(path = "/view/{id}")
    public List<OrderItemData> getOrderByID(@PathVariable int id) throws ApiException {
        return orderDto.getOrderByID(id);
    }

    @ApiOperation(value = "Download Invoice")
    @GetMapping(path = "/invoice/{id}")
    public ResponseEntity<byte[]> getPDF(@PathVariable int id) throws Exception{
        return orderDto.getPDF(id);
    }

}
