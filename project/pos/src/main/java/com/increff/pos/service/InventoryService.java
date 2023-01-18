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


    public void add(InventoryPojo i) throws ApiException {
        InventoryPojo i_temp = getById(i.getId());
        if(Objects.isNull(i_temp)) {
            inventoryDao.insert(i);
        }
        else {
            int prev_qty = i_temp.getQty();
            int new_qty = prev_qty + i.getQty();
            i_temp.setQty(new_qty);
            inventoryDao.update();
        }
    }

    public InventoryPojo get(int id) throws ApiException {
        return CheckIdInventory(id);
    }

    public List<InventoryPojo> getAll() {
        return inventoryDao.selectALL(InventoryPojo.class);
    }

    public void update(int id, InventoryPojo i) throws ApiException{
        InventoryPojo ix = CheckIdInventory(id);
        ix.setQty(i.getQty());
        inventoryDao.update();
    }

    public InventoryPojo getById(int id) throws ApiException {
        InventoryPojo i = inventoryDao.selectByID(id, InventoryPojo.class);
        return i;
    }

    public InventoryPojo CheckIdInventory(int id) throws ApiException {
        InventoryPojo i = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(i)) {
            throw new ApiException("Product is not there in the inventory");
        }
        return i;
    }

    public Integer getQtyById(int id) throws ApiException {
        InventoryPojo i = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(i)) {
            throw new ApiException("The product is not in the inventory");
        }
        return i.getQty();
    }

}
