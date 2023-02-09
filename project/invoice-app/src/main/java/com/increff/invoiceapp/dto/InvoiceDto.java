package com.increff.invoiceapp.dto;

import com.increff.invoiceapp.model.InvoiceForm;
import com.increff.invoiceapp.service.GenerateInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class InvoiceDto {
    @Autowired
    private GenerateInvoiceService service;

    public String getInvoicePDF( InvoiceForm form) throws IOException, Exception {
        return service.generateInvoice(form);
    }

}
