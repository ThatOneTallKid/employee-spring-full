package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;


    public void add(InventoryPojo inventoryPojo) throws ApiException {
        InventoryPojo tempInventoryPojo = getById(inventoryPojo.getId());
        if(Objects.isNull(tempInventoryPojo)) {
            inventoryDao.insert(inventoryPojo);
        }
        else {
            Integer prevQty = tempInventoryPojo.getQty();
            Integer newQty = prevQty + inventoryPojo.getQty();
            tempInventoryPojo.setQty(newQty);
        }
    }

    public List<InventoryPojo> getAll() {
        return inventoryDao.selectALL(InventoryPojo.class);
    }

    public void update(Integer id, InventoryPojo newInventoryPojo) throws ApiException{
        InventoryPojo inventoryPojo = getCheckInventory(id);
        inventoryPojo.setQty(newInventoryPojo.getQty());
    }

    public InventoryPojo getById(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.selectByID(id, InventoryPojo.class);
        return inventoryPojo;
    }

    public InventoryPojo getCheckInventory(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(inventoryPojo)) {
            throw new ApiException("Inventory for the product does not exist");
        }
        return inventoryPojo;
    }

    public void reduceInventory(Integer id, Integer qty) throws ApiException {
        InventoryPojo inventoryPojo = getById(id);
        if(inventoryPojo.getQty() < qty) {
            throw new ApiException("Only " + inventoryPojo.getQty() + " items are available in the inventory");
        }
        inventoryPojo.setQty(inventoryPojo.getQty() - qty);
    }


}
