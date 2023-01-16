package com.increff.employee.dto;

import com.increff.employee.helper.OrderItemFormHelper;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.*;

import static com.increff.employee.helper.OrderItemFormHelper.*;

@Configuration
public class OrderItemDto {

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
            ProductPojo p = productService.getByBarcode(f.getBarcode());

            OrderItemPojo o = convertOrderItemFormToPojo(f, p.getId());
            o.setOrderId(orderPojo.getId());

            OrderItemPojo checkExist = orderItemService.getOrderItemByOrderIdProductId(o.getOrderId(), o.getProductId());
            if (Objects.isNull(checkExist)) {
                reduceInventory(o, p.getId(), f);
            }
        }
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo o = orderItemService.get(id);
        ProductPojo p = productService.get(o.getProductId());
        return convertOrderItemPojoToData(o, p.getBarcode(), p.getName());
    }

    public List<OrderItemData> getAll() throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAll();
        List<OrderItemData> list2 = new ArrayList<>();
        for (OrderItemPojo o : list) {
            ProductPojo px = productService.get(o.getProductId());
            list2.add(convertOrderItemPojoToData(o, px.getBarcode(), px.getName()));
        }
        return list2;
    }

    public List<OrderItemData> getOrderByID(int id) throws ApiException {
        List<OrderItemPojo> list = orderItemService.getOrderItemByOrderItem(id);
        List<OrderItemData> list2 = new ArrayList<>();
        for (OrderItemPojo o : list) {
            ProductPojo px = productService.get(o.getProductId());
            list2.add(convertOrderItemPojoToData(o, px.getBarcode(), px.getName()));
        }
        return list2;
    }


    private void reduceInventory(OrderItemPojo o, int id, OrderItemForm f) throws ApiException {
        int prev_qty = inventoryService.getQtyById(id);
        int remaining_qty = prev_qty - f.getQty();
        InventoryPojo i = new InventoryPojo();
        i.setQty(remaining_qty);
        orderItemService.add(o);
        inventoryService.update(id, i);
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
        InventoryPojo ix = inventoryService.CheckIdInventory(id);
        if (Objects.isNull(ix)) {
            throw new ApiException("Product is not in the Inventory");
        }
        int prev_qty = inventoryService.getQtyById(id);
        if (prev_qty < f.getQty()) {
            throw new ApiException("Not enough quantity present in the inventory");
        }
    }

}
