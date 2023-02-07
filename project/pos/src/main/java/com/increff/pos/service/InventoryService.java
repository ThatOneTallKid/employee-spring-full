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
            int prevQty = tempInventoryPojo.getQty();
            int newQty = prevQty + inventoryPojo.getQty();
            tempInventoryPojo.setQty(newQty);
            inventoryDao.update();
        }
    }

    public List<InventoryPojo> getAll() {
        return inventoryDao.selectALL(InventoryPojo.class);
    }

    public void update(int id, InventoryPojo newInventoryPojo) throws ApiException{
        InventoryPojo inventoryPojo = getCheckInventory(id);
        inventoryPojo.setQty(newInventoryPojo.getQty());
        inventoryDao.update();
    }

    public InventoryPojo getById(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.selectByID(id, InventoryPojo.class);
        return inventoryPojo;
    }

    public InventoryPojo getCheckInventory(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(inventoryPojo)) {
            throw new ApiException("Product is not there in the inventory");
        }
        return inventoryPojo;
    }

    public void reduceInventory(int id, int qty) throws ApiException {
        InventoryPojo inventoryPojo = getById(id);
        if(inventoryPojo.getQty() < qty) {
            throw new ApiException("The product is not in sufficient quantity");
        }
        inventoryPojo.setQty(inventoryPojo.getQty() - qty);
        inventoryDao.update();
    }


}
