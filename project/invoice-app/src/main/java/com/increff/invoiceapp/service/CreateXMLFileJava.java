package com.increff.invoiceapp.service;


import java.io.File;
import java.text.DecimalFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.increff.invoiceapp.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXMLFileJava {

    public static final String xmlFilePath = "C:\\Users\\KIIT\\Desktop\\Projects\\employee-spring-full\\project\\invoice-app\\src\\main\\resources\\xml" +
            "\\Invoice.xml";


    public void createXML(InvoiceForm invoiceForm) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            DecimalFormat df = new DecimalFormat("#.##");

            // root element
            Element root = document.createElement("invoice");
            document.appendChild(root);


            Element order_id = document.createElement("order_id");
            order_id.appendChild(document.createTextNode(invoiceForm.getOrderId().toString()));
            root.appendChild(order_id);

            Element order_date = document.createElement("order_date");
            order_date.appendChild(document.createTextNode(invoiceForm.getPlaceDate()));
            root.appendChild(order_date);
            int sn = 0;
            // order item element
            for (OrderItem o : invoiceForm.getOrderItemList()){
                Element order_item = document.createElement("order_item");
                sn++;
                root.appendChild(order_item);

                // set an attribute to staff element
                Element id = document.createElement("sn");
                id.appendChild(document.createTextNode(Integer.toString(sn)));
                order_item.appendChild(id);

                // firstname element
                Element ProductId = document.createElement("product_name");
                ProductId.appendChild(document.createTextNode(o.getProductName()));
                order_item.appendChild(ProductId);

                // lastname element
                Element quantity = document.createElement("quantity");
                quantity.appendChild(document.createTextNode(o.getQuantity().toString()));
                order_item.appendChild(quantity);

                Element sellingPrice = document.createElement("selling_price");
                sellingPrice.appendChild(document.createTextNode(Double.valueOf(df.format(o.getSellingPrice())).toString()));
                order_item.appendChild(sellingPrice);

                Element amt = document.createElement("amt");
                amt.appendChild(document.createTextNode(Double.valueOf(df.format(o.getAmt())).toString()));
                order_item.appendChild(amt);

            }

            Element amount = document.createElement("amount");
            amount.appendChild(document.createTextNode(Double.valueOf(df.format(invoiceForm.getAmount())).toString()));
            root.appendChild(amount);
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}