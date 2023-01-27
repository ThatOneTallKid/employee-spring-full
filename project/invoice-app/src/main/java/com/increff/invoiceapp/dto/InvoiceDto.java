package com.increff.invoiceapp.dto;

import com.increff.invoiceapp.model.BrandReportForm;
import com.increff.invoiceapp.model.InventoryReportForm;
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

    public ResponseEntity<byte[]> getInvoicePDF(@RequestBody InvoiceForm form) throws IOException {

        service.generateInvoice(form);
        String _filename = "./Test/invoice_"+form.getOrderId() +".pdf";
        Path pdfPath = Paths.get("./Test/invoice.pdf");

        byte[] contents = Base64.getEncoder().encode(Files.readAllBytes(pdfPath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    public ResponseEntity<byte[]> getBrandReport(@RequestBody BrandReportForm form) throws IOException {

        service.generateBrandReport(form);
        Path pdfPath = Paths.get("./Test/brandreport.pdf");

        byte[] contents = Files.readAllBytes(pdfPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    public ResponseEntity<byte[]> getInventoryReport(@RequestBody InventoryReportForm form) throws IOException {

        service.generateInventoryReport(form);
        Path pdfPath = Paths.get("./Test/inventoryreport.pdf");

        byte[] contents = Files.readAllBytes(pdfPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
}
