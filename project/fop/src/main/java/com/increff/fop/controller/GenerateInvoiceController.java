package com.increff.fop.controller;

import com.increff.fop.dto.InvoiceDto;
import com.increff.fop.model.BrandReportForm;
import com.increff.fop.model.InventoryReportForm;
import com.increff.fop.model.InvoiceForm;
import com.increff.fop.service.GenerateInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api
@RestController
public class GenerateInvoiceController {

    @Autowired
    private InvoiceDto invoiceDto;

    @ApiOperation(value = "Generate Invoice")
    @RequestMapping(path = "/api/invoice", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPDF(@RequestBody InvoiceForm form) throws IOException {
        return invoiceDto.getInvoicePDF(form);
    }

    @ApiOperation(value = "Generate Brand Report")
    @RequestMapping(path = "/api/brandreport", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPDF(@RequestBody BrandReportForm form) throws IOException {
        return invoiceDto.getBrandReport(form);
    }

    @ApiOperation(value = "Generate Inventory Report")
    @RequestMapping(path = "/api/inventoryreport", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPDF(@RequestBody InventoryReportForm form) throws IOException {
        return invoiceDto.getInventoryReport(form);
    }

}