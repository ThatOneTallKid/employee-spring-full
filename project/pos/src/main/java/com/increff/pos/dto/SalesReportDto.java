package com.increff.pos.dto;

import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.SalesReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class SalesReportDto {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    public List<SalesReportData> getAll() throws ApiException {
        List<OrderPojo> list = orderService.getAllOrders();
        return getFilterSalesReport(list, "all", "all");
    }

    public List<SalesReportData> getFilteredData(SalesReportForm salesReportForm) throws ApiException {
        String startDate = salesReportForm.getStartDate() + " 00:00:00";
        String endDate = salesReportForm.getEndDate() + " 23:59:59";
        ValidationUtil.validateForms(salesReportForm);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime sDate = LocalDateTime.parse(startDate, formatter);
        LocalDateTime eDate = LocalDateTime.parse(endDate, formatter);
        List<OrderPojo> list = orderService.getOrderByDateFilter(sDate,eDate);
        return getFilterSalesReport(list,salesReportForm.getBrand(), salesReportForm.getCategory());
    }

    public List<SalesReportData> getFilterSalesReport(List<OrderPojo> list, String brand, String category) throws ApiException {
        HashMap<Integer, SalesReportData> map = new HashMap<Integer, SalesReportData>();
        for(OrderPojo orderPojo: list){
            List<OrderItemPojo> orderItemPojoList = orderService.getOrderItemsByOrderId(orderPojo.getId());
            for (OrderItemPojo orderItemPojo: orderItemPojoList) {
                ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
                BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
                if(!map.containsKey(brandPojo.getId())) {
                    map.put(brandPojo.getId(), new SalesReportData());
                }
                SalesReportData salesReportData = map.get(brandPojo.getId());
                salesReportData.setQuantity(salesReportData.getQuantity() + orderItemPojo.getQty() );
                salesReportData.setRevenue(salesReportData.getRevenue() + (orderItemPojo.getQty() * orderItemPojo.getSellingPrice()));

            }
        }

        List<SalesReportData> salesReportDataList = new ArrayList<>();

        for(Map.Entry<Integer, SalesReportData> entry: map.entrySet()) {
            BrandPojo bp = brandService.getCheck(entry.getKey());
            if((Objects.equals(brand,bp.getBrand()) || Objects.equals(brand,"all")) && (Objects.equals(category,bp.getCategory()) || Objects.equals(category,"all"))){
                SalesReportData d = entry.getValue();
                d.setBrand(bp.getBrand());
                d.setCategory(bp.getCategory());
                salesReportDataList.add(d);
            }
        }

        return salesReportDataList;
    }
}
