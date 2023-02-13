package com.increff.invoiceapp.dto;

import com.increff.invoiceapp.model.InvoiceForm;
import com.increff.invoiceapp.service.GenerateInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateInvoiceDto {
    @Autowired
    private GenerateInvoiceService service;

    public String getInvoicePDF( InvoiceForm form) throws Exception {
        return service.generateInvoice(form);
    }

}
