package com.increff.pos.util;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckerUtil {

    @Autowired
    private static InventoryDao inventoryDao;

    @Autowired
    private static ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

}
