package com.increff.pos.dto;

import com.increff.pos.model.data.OrderData;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.model.form.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.increff.pos.helper.OrderFormHelper.convertOrderPojoToData;
import static com.increff.pos.helper.OrderItemFormHelper.*;

@Component
public class OrderDto {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;


    public void add(List<OrderItemForm> forms) throws ApiException {
        checkDuplicates(forms);

        OrderPojo orderPojo = new OrderPojo();
        orderService.add(orderPojo);

        for (OrderItemForm f : forms) {
            // Check if Barcode exists in product db
            ProductPojo productPojo = productService.getByBarcode(f.getBarcode());

            OrderItemPojo orderItemPojo = convertOrderItemFormToPojo(f, productPojo.getId());
            orderItemPojo.setOrderId(orderPojo.getId());

            OrderItemPojo checkExist = orderItemService.getOrderItemByOrderIdProductId(orderItemPojo.getOrderId(),
                    orderItemPojo.getProductId());
            if (Objects.isNull(checkExist)) {
                reduceInventory(orderItemPojo, productPojo.getId(), f);
            }
        }
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemService.get(id);
        ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
        return convertOrderItemPojoToData(orderItemPojo, productPojo.getBarcode(), productPojo.getName());
    }

    public List<OrderData> getAll() {
        List<OrderPojo> list = orderService.getAll();
        List<OrderData> list2 = new ArrayList<>();
        for(OrderPojo orderPojo : list) {
            list2.add(convertOrderPojoToData(orderPojo));
        }
        return list2;
    }

    public List<OrderItemData> getOrderByID(int id) throws ApiException {
        List<OrderItemPojo> list = orderItemService.getOrderItemByOrderItem(id);
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
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQty(remaining_qty);
        orderItemService.add(orderItemPojo);
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
            checkInventory(productService.getIDByBarcode(f.getBarcode()), f);
        }

    }

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
