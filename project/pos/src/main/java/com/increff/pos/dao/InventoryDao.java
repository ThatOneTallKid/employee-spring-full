package com.increff.pos.dao;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class InventoryDao extends AbstractDao{

    public void update() {
        // Empty funtion just for service layer
    }
}
