package com.increff.employee.util;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class CheckerUtil {

    @Autowired
    private static InventoryDao inventoryDao;

    @Autowired
    private static ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

}
