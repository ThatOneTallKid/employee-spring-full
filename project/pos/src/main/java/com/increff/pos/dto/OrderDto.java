package com.increff.pos.dto;

import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItem;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.InvoiceForm;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
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

    @Value("${invoice.url}")
    private String url;

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemForm> forms) throws ApiException {
        Map<String, ProductPojo> productList = new HashMap<>();
        productList.clear();
        productList= getProductList(productList);

        checkDuplicates(forms);
        checkInventory(forms, productList);

        OrderPojo orderPojo = new OrderPojo();
        orderService.addOrder(orderPojo);

        for (OrderItemForm f : forms) {
            if(productList.containsKey(f.getBarcode())) {
                ProductPojo productPojo = productList.get(f.getBarcode());
                OrderItemPojo orderItemPojo = convertOrderItemFormToPojo(f, productPojo.getId());
                orderItemPojo.setOrderId(orderPojo.getId());
                reduceInventory(orderItemPojo, productPojo.getId(), f);
            }
            else {
                throw new ApiException("Product with given Barcode does not exists !");
            }
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
        InvoiceForm invoiceForm = generateInvoiceForOrder(id);

        RestTemplate restTemplate = new RestTemplate();
        byte[] contents = Base64.getDecoder().decode(restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    public List<OrderItemData> getOrderItemsByID(int id) throws ApiException {
        List<OrderItemPojo> list = orderService.getOrderItemsByOrderId(id);
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
        if(remaining_qty < 0) {
            throw new ApiException("Not enough items in the inventory");
        }
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQty(remaining_qty);
        orderService.add(orderItemPojo);
        inventoryService.update(id, inventoryPojo);
    }

    private void checkDuplicates(List<OrderItemForm> forms) throws ApiException{
        Set<String> set = new HashSet<>();
        for(OrderItemForm f : forms) {
            ValidationUtil.validateForms(f);
            normalizeOrderItem(f);
            if(set.contains(f.getBarcode())) {
                throw new ApiException("Duplicate Barcode Detected");
            }
            set.add(f.getBarcode());
        }
    }

    private void checkInventory(List<OrderItemForm> orderItemFormList, Map<String, ProductPojo> productPojoList) throws ApiException {
        for (OrderItemForm form: orderItemFormList) {
            if(productPojoList.containsKey(form.getBarcode())) {
                InventoryPojo inventoryPojo = inventoryService.CheckIdInventory(productPojoList.get(form.getBarcode()).getId());
            }
            else {
                throw new ApiException("Product is not in the Inventory");
            }
            int prev_qty = inventoryService.getQtyById(productPojoList.get(form.getBarcode()).getId());
            if (prev_qty < form.getQty()) {
                throw new ApiException("Not enough quantity present in the inventory");
            }
        }
    }

    private Map<String, ProductPojo> getProductList(Map<String, ProductPojo> productList) {
        List<ProductPojo> list = productService.getAll();
        for (ProductPojo productPojo: list){
            productList.put(productPojo.getBarcode(), productPojo);
        }
        return productList;
    }

    private InvoiceForm generateInvoiceForOrder(int orderId) throws ApiException
    {
        InvoiceForm invoiceForm = new InvoiceForm();
        OrderPojo orderPojo = orderService.getOrderById(orderId);

        invoiceForm.setOrderId(orderPojo.getId());
        invoiceForm.setPlaceDate(orderPojo.getCreatedAt().toString());

        List<OrderItemPojo> orderItemPojoList = orderService.getOrderItemsByOrderId(orderPojo.getId());
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderItemPojo p: orderItemPojoList)
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(p.getId());
            String productName = productService.get(p.getProductId()).getName();
            orderItem.setProductName(productName);
            orderItem.setQuantity(p.getQty());
            orderItem.setSellingPrice(p.getSellingPrice());
            orderItemList.add(orderItem);
        }

        invoiceForm.setOrderItemList(orderItemList);

        return invoiceForm;
    }

}
