package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Autowired
    private ProductDao p_dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo i) throws ApiException {
        if(i.getQty() == 0) {
            throw new ApiException("Quantity cannot be Empty");
        }
        InventoryPojo i_temp = getCheck(i.getId());
        if(i_temp == null) {
            dao.insert(i);
        }
        else {
            int prev_qty = i_temp.getQty();
            int new_qty = prev_qty + i.getQty();
            i_temp.setQty(new_qty);
            dao.update();
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectALL(InventoryPojo.class, "InventoryPojo");
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, InventoryPojo i) throws ApiException{
        InventoryPojo ix = getCheck(id);
        ix.setQty(i.getQty());
        dao.update();
    }

    @Transactional
    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo i = dao.selectByID(id, InventoryPojo.class, "InventoryPojo");

        ProductPojo p = p_dao.selectByID(id, ProductPojo.class, "ProductPojo");
        if(p == null) {
            throw new ApiException("Product not found in the Product Database !");
        }
        return i;
    }

    @Transactional
    public Integer getIDByBarcode(String barcode) throws ApiException {
        ProductPojo p = p_dao.selectByBarcode(barcode);
        if(p == null) {
            throw new ApiException("Barcode Not found in Prodcut Database!");
        }
        return p.getId();

    }

    @Transactional
    public String getBarcodeByID(int id) throws ApiException {
        ProductPojo p = p_dao.selectByID(id, ProductPojo.class, "ProductPojo");
        if(p == null) {
            throw new ApiException("Barcode Not found in Prodcut Database!");
        }
        return p.getBarcode();

    }
}
