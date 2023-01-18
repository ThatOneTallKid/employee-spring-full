package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.invoice.InvoiceGenerator;
import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.InvoiceForm;
import com.increff.pos.model.form.OrderItemForm;
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

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto orderItem;

    @Autowired
    InvoiceGenerator invoiceGenerator;

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

    @ApiOperation(value = "Download Invoice")
    @RequestMapping(path = "/api/invoice/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF(@PathVariable int id) throws Exception{
        System.out.println("here");
        InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(id);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/fop/api/invoice";

        byte[] contents = restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody();
        System.out.println("here 2");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

}
