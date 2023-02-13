package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/order")
public class OrderApiController {

    @Autowired
    private OrderDto orderDto;

    @Value("${invoice.url}")
    private String invoiceUrl;

    @ApiOperation(value = "Adds all Order Items and places an order")
    @RequestMapping(path = "/item", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> form) throws ApiException {
        orderDto.add(form);
    }

    @ApiOperation(value ="Gets all orders")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return orderDto.getAll();
    }

    @ApiOperation(value ="Gets all order items by order ID")
    @RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getOrderByID(@PathVariable Integer id) throws ApiException {
        return orderDto.getOrderItemsByID(id);
    }

    @ApiOperation(value = "Download Order Invoice")
    @RequestMapping(path = "/invoice/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF(@PathVariable Integer id) throws Exception{
        return orderDto.getPDF(id, this.invoiceUrl);
    }

}
