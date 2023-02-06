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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.increff.pos.dtoUtil.OrderFormHelper.convertOrderPojoToData;
import static com.increff.pos.dtoUtil.OrderItemFormHelper.*;

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

    @Value("${invoice.pdf_path}")
    private static String PDF_PATH;

    // TODO: ask doubts
    // icons and buttons
    // total rows

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemForm> forms) throws ApiException {
        checkDuplicates(forms);
        Map<String, ProductPojo> productList = new HashMap<>();
        productList= getProductList(productList, forms);

        checkInventory(forms, productList);

        OrderPojo orderPojo = new OrderPojo();
        orderService.addOrder(orderPojo);

        for (OrderItemForm f : forms) {
                ProductPojo productPojo = productList.get(f.getBarcode());
                OrderItemPojo orderItemPojo = convertOrderItemFormToPojo(f, productPojo.getId());
                orderItemPojo.setOrderId(orderPojo.getId());
                reduceInventory(orderItemPojo, productPojo.getId(), f);
        }
    }


    public List<OrderData> getAll() {
        List<OrderPojo> list = orderService.getAllOrders();
        List<OrderData> list2 = new ArrayList<>();
        for(OrderPojo orderPojo : list) {
            list2.add(convertOrderPojoToData(orderPojo));
        }
        return list2;
    }

    public ResponseEntity<byte[]> getPDF(int id, String _url) throws Exception {
        InvoiceForm invoiceForm = generateInvoiceForOrder(id);

        RestTemplate restTemplate = new RestTemplate();
        byte[] contents = Base64.getDecoder().decode(restTemplate.postForEntity(_url, invoiceForm, byte[].class).getBody());

        Path pdfPath = Paths.get(PDF_PATH +id+"invoice.pdf");

        Files.write(pdfPath, contents);
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

    // IMprove
    // TODO: MOve this to inventory service - done
    private void reduceInventory(OrderItemPojo orderItemPojo, int id, OrderItemForm orderItemForm) throws ApiException{
        inventoryService.reduceInventory(id, orderItemForm.getQty());
        orderService.add(orderItemPojo);
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
    //TODO: Improve this method - Done
    private void checkInventory(List<OrderItemForm> orderItemFormList, Map<String, ProductPojo> productPojoList) throws ApiException {
        for (OrderItemForm form: orderItemFormList) {
            InventoryPojo inventoryPojo = inventoryService.CheckIdInventory(productPojoList.get(form.getBarcode()).getId());
            if (inventoryPojo.getQty() < form.getQty()) {
                throw new ApiException("Not enough quantity present in the inventory");
            }
        }
    }

    private Map<String, ProductPojo> getProductList(Map<String, ProductPojo> productList, List<OrderItemForm> forms ) throws ApiException {
        // TODO: Improve this method, IN query - done
        List<String> barcodeList = new ArrayList<>();
        for(OrderItemForm f: forms) {
            barcodeList.add(f.getBarcode());
        }
        List<ProductPojo> productPojoList = productService.selectInBarcode(barcodeList);
        for(ProductPojo p: productPojoList) {
            productList.put(p.getBarcode(), p);
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
