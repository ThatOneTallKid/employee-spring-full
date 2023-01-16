package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;


    @Transactional(rollbackOn = ApiException.class)
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

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return CheckIdInventory(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return inventoryDao.selectALL(InventoryPojo.class);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, InventoryPojo i) throws ApiException{
        InventoryPojo ix = CheckIdInventory(id);
        ix.setQty(i.getQty());
        inventoryDao.update();
    }

    @Transactional
    public InventoryPojo getById(int id) throws ApiException {
        InventoryPojo i = inventoryDao.selectByID(id, InventoryPojo.class);
        return i;
    }

    @Transactional
    public InventoryPojo CheckIdInventory(int id) throws ApiException {
        InventoryPojo i = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(i)) {
            throw new ApiException("Product is not there in the inventory");
        }
        return i;
    }

    @Transactional
    public Integer getQtyById(int id) throws ApiException {
        InventoryPojo i = inventoryDao.selectByID(id, InventoryPojo.class);
        if(Objects.isNull(i)) {
            throw new ApiException("The product is not in the inventory");
        }
        return i.getQty();
    }

}
