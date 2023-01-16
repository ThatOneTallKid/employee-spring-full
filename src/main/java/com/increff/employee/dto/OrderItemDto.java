package com.increff.employee.dto;

import com.increff.employee.helper.OrderItemFormValidator;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.ConvertUtil;
import com.increff.employee.util.StringUtil;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.ConvertUtil.convertOrderItemFormToPojo;

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
        for (OrderItemForm f : forms) {
            StringUtil.normalizeOrderItemForm(f);


            // Check if Barcode exists in product db
            int id = productService.getIDByBarcode(f.getBarcode());
            ProductPojo p = productService.get(id);

            // check if product is in inventory
            InventoryPojo ix = inventoryService.CheckIdInventory(id);
            if (ValidationUtil.checkNull(ix)) {
                throw new ApiException("Product is not in the Inventory");
            }

            // validate the form
            OrderItemFormValidator.validate(f);

            // Check If there is sufficient item in the inventory
            // edit th
            checkInventory(id, f);
        }

        OrderPojo orderPojo = new OrderPojo();
        orderService.add(orderPojo);

        for (OrderItemForm f : forms) {

            StringUtil.normalizeOrderItemForm(f);

            // Check if Barcode exists in product db
            int id = productService.getIDByBarcode(f.getBarcode());
            ProductPojo p = productService.get(id);

            OrderItemPojo o = convertOrderItemFormToPojo(f, p.getId());
            o.setOrderId(orderPojo.getId());

            OrderItemPojo checkExist = orderItemService.getOrderItemByOrderIdProductId(o.getOrderId(), o.getProductId());
            if (ValidationUtil.checkNull(checkExist)) {
                reduceInventory(o, id, f);
            } else {
                f.setQty(f.getQty() + checkExist.getQty());
                update(checkExist.getId(), f);
            }
        }
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo o = orderItemService.get(id);
        ProductPojo p = productService.get(o.getProductId());
        return ConvertUtil.convertOrderItemPojoToData(o, p.getBarcode());
    }

    public List<OrderItemData> getAll() throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAll();
        List<OrderItemData> list2 = new ArrayList<>();
        for (OrderItemPojo o : list) {
            ProductPojo px = productService.get(o.getProductId());
            list2.add(ConvertUtil.convertOrderItemPojoToData(o, px.getBarcode()));
        }
        return list2;
    }

    public List<OrderItemData> getOrderByID(int id) throws ApiException {
        List<OrderItemPojo> list = orderItemService.getOrderItemByOrderItem(id);
        List<OrderItemData> list2 = new ArrayList<>();
        for (OrderItemPojo o : list) {
            ProductPojo px = productService.get(o.getProductId());
            list2.add(ConvertUtil.convertOrderItemPojoToData(o, px.getBarcode()));
        }
        return list2;
    }

    public void update(int id, OrderItemForm form) throws ApiException {
        if(form.getQty() <= 0) {
            throw new ApiException("Quantity cannot be zero or negative");
        }
        StringUtil.normalizeOrderItemForm(form);
        int pid = productService.getIDByBarcode(form.getBarcode());
        ProductPojo p = productService.get(pid);

        // Logic needs checking
        int prev_qty = inventoryService.getQtyById(pid);
        int cur_qty = orderItemService.get(id).getQty();
        int new_qty = prev_qty + cur_qty;
        if (new_qty < form.getQty()) {
            throw new ApiException("Not enough quantity present in the inventory");
        }
        int rem_qty = new_qty - form.getQty();
        InventoryPojo i = new InventoryPojo();
        i.setQty(rem_qty);
        OrderItemPojo o = convertOrderItemFormToPojo(form, p.getId());

        orderItemService.update(id, o);
        inventoryService.update(pid, i);
    }

    private void reduceInventory(OrderItemPojo o, int id, OrderItemForm f) throws ApiException {
        int prev_qty = inventoryService.getQtyById(id);
        int remaining_qty = prev_qty - f.getQty();
        InventoryPojo i = new InventoryPojo();
        // TODO: Inventory will be updated only after the order has been placed
        i.setQty(remaining_qty);
        orderItemService.add(o);
        inventoryService.update(id, i);
    }

    private void checkInventory(int id, OrderItemForm f) throws ApiException {
        int prev_qty = inventoryService.getQtyById(id);
        if (prev_qty < f.getQty()) {
            throw new ApiException("Not enough quantity present in the inventory");
        }
    }
}
