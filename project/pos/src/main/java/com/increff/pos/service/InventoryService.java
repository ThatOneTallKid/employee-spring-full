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
            //TODO all variables will be camelCase
            int prev_qty = tempInventoryPojo.getQty();
            int new_qty = prev_qty + inventoryPojo.getQty();
            tempInventoryPojo.setQty(new_qty);
            inventoryDao.update();
        }
    }

    //TODO check if this function is required?
    public InventoryPojo get(int id) throws ApiException {
        return CheckIdInventory(id);
    }

    public List<InventoryPojo> getAll() {
        return inventoryDao.selectALL(InventoryPojo.class);
    }

    public void update(int id, InventoryPojo newInventoryPojo) throws ApiException{
        InventoryPojo inventoryPojo = CheckIdInventory(id);
        inventoryPojo.setQty(newInventoryPojo.getQty());
        inventoryDao.update();
    }

    public InventoryPojo getById(int id) throws ApiException {
        return inventoryDao.selectByID(id, InventoryPojo.class);
    }

    public InventoryPojo CheckIdInventory(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(inventoryPojo)) {
            throw new ApiException("Product is not there in the inventory");
        }
        return inventoryPojo;
    }

    public Integer getQtyById(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(inventoryPojo)) {
            throw new ApiException("The product is not in the inventory");
        }
        return inventoryPojo.getQty();
    }

}
