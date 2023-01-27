package com.increff.pos.dto;

import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.InvoiceForm;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.increff.pos.invoice.InvoiceGenerator;

import java.util.*;

import static com.increff.pos.helper.OrderFormHelper.convertOrderPojoToData;
import static com.increff.pos.helper.OrderItemFormHelper.*;

@Component
public class OrderDto {

    @Autowired
    OrderService orderService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    @Autowired
    InvoiceGenerator invoiceGenerator;

    //TODO add transactional
    public void add(List<OrderItemForm> forms) throws ApiException {
        checkDuplicates(forms);

        OrderPojo orderPojo = new OrderPojo();
        orderService.addOrder(orderPojo);

        //TODO fetch product from DB using list
        for (OrderItemForm f : forms) {
            ProductPojo productPojo = productService.getByBarcode(f.getBarcode());

            OrderItemPojo orderItemPojo = convertOrderItemFormToPojo(f, productPojo.getId());
            orderItemPojo.setOrderId(orderPojo.getId());
            reduceInventory(orderItemPojo, productPojo.getId(), f);
        }
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo orderItemPojo = orderService.get(id);
        ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
        return convertOrderItemPojoToData(orderItemPojo, productPojo.getBarcode(), productPojo.getName());
    }

    public List<OrderData> getAll() {
        List<OrderPojo> list = orderService.getAllOrders();
        List<OrderData> list2 = new ArrayList<>();
        for(OrderPojo orderPojo : list) {
            list2.add(convertOrderPojoToData(orderPojo));
        }
        return list2;
    }

    public ResponseEntity<byte[]> getPDF(int id) throws Exception {
        InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(id);

        RestTemplate restTemplate = new RestTemplate();

        //TODO this url in properties file
        String url = "http://localhost:8085/fop/api/invoice";

        byte[] contents = Base64.getDecoder().decode(restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    public List<OrderItemData> getOrderByID(int id) throws ApiException {
        List<OrderItemPojo> list = orderService.getOrderItemByOrderItem(id);
        List<OrderItemData> list2 = new ArrayList<>();
        for (OrderItemPojo orderItemPojo : list) {
            ProductPojo px = productService.get(orderItemPojo.getProductId());
            list2.add(convertOrderItemPojoToData(orderItemPojo, px.getBarcode(), px.getName()));
        }
        return list2;
    }


    private void reduceInventory(OrderItemPojo orderItemPojo, int id, OrderItemForm orderItemForm) throws ApiException{
        int prev_qty = inventoryService.getQtyById(id);
        int remaining_qty = prev_qty - orderItemForm.getQty();
        //TODO check for negative remaing qty
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQty(remaining_qty);
        orderService.add(orderItemPojo);
        inventoryService.update(id, inventoryPojo);
    }

    //TODO this function should only check duplicates
    private void checkDuplicates(List<OrderItemForm> forms) throws ApiException{
        Set<String> set = new HashSet<>();
        for(OrderItemForm f : forms) {
            ValidationUtil.validateForms(f);
            normalizeOrderItem(f);
            if(set.contains(f.getBarcode())) {
                throw new ApiException("Duplicate Barcode Detected");
            }
            set.add(f.getBarcode());
            checkInventory(productService.getIDByBarcode(f.getBarcode()), f);
        }

    }

    //TODO make this function check inventory for list
    private void checkInventory(int id, OrderItemForm f) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.CheckIdInventory(id);
        if (Objects.isNull(inventoryPojo)) {
            throw new ApiException("Product is not in the Inventory");
        }
        int prev_qty = inventoryService.getQtyById(id);
        if (prev_qty < f.getQty()) {
            throw new ApiException("Not enough quantity present in the inventory");
        }
    }

}
