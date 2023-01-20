package com.increff.pos.service;

import com.increff.pos.dao.SalesDao;
import com.increff.pos.pojo.SalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalesService {
    @Autowired
    SalesDao salesDao;

    @Transactional
    public void add(SalesPojo salesPojo){
        salesDao.insert(salesPojo);
    }

    @Transactional
    public List<SalesPojo> getALL() {
        return salesDao.selectALL(SalesPojo.class);
    }

    @Transactional
    public SalesPojo getByDate(LocalDate date) {
        return salesDao.selectByDate(date);
    }

    @Transactional
    public void update(LocalDate date, SalesPojo newPojo)
    {
        SalesPojo pojo = salesDao.selectByDate(date);
        pojo.setInvoicedOrderCount(newPojo.getInvoicedOrderCount());
        pojo.setTotalRevenue(newPojo.getTotalRevenue());
        pojo.setInvoicedItemsCount(newPojo.getInvoicedItemsCount());
    }

}
