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
    public static final String brandXmlFilePath = "C:\\Users\\KIIT\\Desktop\\Projects\\employee-spring-full\\project\\invoice-app\\src\\main\\resources\\xml" +
            "\\brand.xml";
    public static final String inventoryXmlFilePath = "C:\\Users\\KIIT\\Desktop\\Projects\\employee-spring-full\\project\\invoice-app\\src\\main\\resources\\xml" +
            "\\inventory.xml";


    public void createXML(InvoiceForm invoiceForm) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("invoice");
            document.appendChild(root);


            Element order_id = document.createElement("order_id");
            order_id.appendChild(document.createTextNode(invoiceForm.getOrderId().toString()));
            root.appendChild(order_id);

            Element order_date = document.createElement("order_date");
            order_date.appendChild(document.createTextNode(invoiceForm.getPlaceDate()));
            root.appendChild(order_date);

            // order item element
            for (OrderItem o : invoiceForm.getOrderItemList()){
                DecimalFormat df = new DecimalFormat("#.##");
                Element order_item = document.createElement("order_item");

                root.appendChild(order_item);

                // set an attribute to staff element
                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(o.getOrderItemId().toString()));
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
            amount.appendChild(document.createTextNode(invoiceForm.getAmount().toString()));
            root.appendChild(amount);
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void createBrandXml(BrandReportForm form) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("brand_table");
            document.appendChild(root);


            // order item element
            for (BrandData o : form.getBrandItems()){
                Element brand_item = document.createElement("brand_item");

                root.appendChild(brand_item);

                // set an attribute to staff element
                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(o.getId().toString()));
                brand_item.appendChild(id);

                // firstname element
                Element brand = document.createElement("brand");
                brand.appendChild(document.createTextNode(o.getBrand()));
                brand_item.appendChild(brand);

                // lastname element
                Element category = document.createElement("category");
                category.appendChild(document.createTextNode(o.getCategory()));
                brand_item.appendChild(category);

            }

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(brandXmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }


    public void createInventoryXml(InventoryReportForm form) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("inventory_table");
            document.appendChild(root);


            // order item element
            for (InventoryItem o : form.getInventoryDataList()){
                Element inventory_item = document.createElement("inventory_item");

                root.appendChild(inventory_item);

                // set an attribute to staff element
                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(Integer.toString(o.getId())));
                inventory_item.appendChild(id);

                // firstname element
                Element brand = document.createElement("brand");
                brand.appendChild(document.createTextNode(o.getBrand()));
                inventory_item.appendChild(brand);

                // lastname element
                Element category = document.createElement("category");
                category.appendChild(document.createTextNode(o.getCategory()));
                inventory_item.appendChild(category);

                Element qty = document.createElement("qty");
                qty.appendChild(document.createTextNode(Integer.toString(o.getQty())));
                inventory_item.appendChild(qty);

            }

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(inventoryXmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}