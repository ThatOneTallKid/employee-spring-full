package com.increff.employee.dto;

import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderForm;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.*;
import com.increff.employee.service.*;
import com.increff.employee.util.ConvertUtil;
import com.increff.employee.util.StringUtil;
import com.increff.employee.util.ValidationUtil;
import org.hibernate.criterion.Order;
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
        OrderPojo orderPojo = new OrderPojo();
        System.out.println("addabove");
        orderService.add(orderPojo);
        System.out.println("addbelow");
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
            if (StringUtil.isEmpty(f.getBarcode())) {
                throw new ApiException("Barcode cannot be empty");
            }
            if (f.getSellingPrice() == 0) {
                throw new ApiException("Selling price cannot be Empty or Zero");
            }
            if (f.getQty() <= 0) {
                throw new ApiException("Quantity cannot be Empty, zero or negative");
            }

            // Check If there is suffuicient item in the inventory
            checkInventory(id, f);

            OrderItemPojo o = convertOrderItemFormToPojo(f, p.getId());
            o.setOrderId(orderPojo.getId());

            OrderItemPojo checkExist = orderItemService.getOrderItemByOrderIdProductId(o.getOrderId(), o.getProductId());
            if (ValidationUtil.checkNull(checkExist)) {
                reduceInventory(o, id, f);
            }
            else {
                f.setQty(f.getQty() + checkExist.getQty());
                update(checkExist.getId(), f);
            }
        }
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo o = orderItemService.get(id);
        ProductPojo p = productService.get(o.getProductId());
        return ConvertUtil.convertOrderItemPojoToData(o,p.getBarcode());
    }

    public List<OrderItemData> getAll() throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAll();
        List<OrderItemData> list2 = new ArrayList<>();
        for(OrderItemPojo o : list) {
            ProductPojo px = productService.get(o.getProductId());
            list2.add(ConvertUtil.convertOrderItemPojoToData(o, px.getBarcode()));
        }
        return list2;
    }

    public void update(int id, OrderItemForm form) throws ApiException{
        StringUtil.normalizeOrderItemForm(form);
        int pid = productService.getIDByBarcode(form.getBarcode());
        ProductPojo p = productService.get(pid);

        // Logic needs checking
        int prev_qty = inventoryService.getQtyById(pid);
        int cur_qty = orderItemService.get(id).getQty();
        int new_qty = prev_qty + cur_qty;
        if(new_qty < form.getQty()) {
            throw new ApiException("Not enough quantity present in the inventory");
        }
        int rem_qty = new_qty- form.getQty();
        InventoryPojo i = new InventoryPojo();
        i.setQty(rem_qty);
        OrderItemPojo o = convertOrderItemFormToPojo(form, p.getId());

        orderItemService.update(id, o);
        inventoryService.update(pid, i);
    }

    private void reduceInventory(OrderItemPojo o,  int id, OrderItemForm f) throws ApiException {
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
