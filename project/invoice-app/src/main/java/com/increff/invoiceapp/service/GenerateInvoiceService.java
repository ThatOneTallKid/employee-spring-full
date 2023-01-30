package com.increff.invoiceapp.service;


import com.increff.invoiceapp.model.InvoiceForm;
import com.increff.invoiceapp.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateInvoiceService {
    public void generateInvoice(InvoiceForm form)
    {
//        print(form);
//        InvoiceForm invoiceForm = new InvoiceForm();
//        invoiceForm.setCustomerName("Aayush");
//        invoiceForm.setOrderId(1);
//        invoiceForm.setPlaceDate("12/12/12");
//
//        List<OrderItem> orderItemList = new ArrayList<>();
//
//        for(int i=1; i<=5; i++)
//        {
//            OrderItem p = new OrderItem();
//            p.setOrderItemId(i);
//            p.setProductName("string" + i);
//            p.setSellingPrice(10.00 + i);
//            p.setQuantity(1+i);
//            orderItemList.add(p);
//        }
//
//        invoiceForm.setOrderItemList(orderItemList);
        List<OrderItem> items = form.getOrderItemList();
        Double amt = 0.0;
        for(OrderItem i : items) {
            Double cur = 0.0;
            cur = i.getSellingPrice() * i.getQuantity();
            amt+=cur;
            i.setAmt(cur);
        }
        form.setAmount(amt);
        CreateXMLFileJava createXMLFileJava = new CreateXMLFileJava();

        createXMLFileJava.createXML(form);

        PDFFromFOP pdfFromFOP = new PDFFromFOP();

        pdfFromFOP.createPDF();
    }

    private void print(InvoiceForm form)
    {
        System.out.println(form.getOrderId());
        System.out.println(form.getPlaceDate());

        List<OrderItem> orderItemList = form.getOrderItemList();

        System.out.println(orderItemList.size());

        for(OrderItem o :orderItemList)
        {
            System.out.println(o.getProductName());
            System.out.println(o.getOrderItemId());
            System.out.println(o.getQuantity());
            System.out.println(o.getSellingPrice());
        }
    }


}