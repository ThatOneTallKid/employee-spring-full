package com.increff.pos.dto;

import com.increff.pos.dtoUtil.SalesReportFormHelper;
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
import com.increff.pos.util.CsvFileGenerator;
import com.increff.pos.util.DateValidatorUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class    SalesReportDto {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CsvFileGenerator csvGenerator;

    protected List<SalesReportData> salesList = new ArrayList<>();


    public List<SalesReportData> getFilteredData(SalesReportForm salesReportForm) throws ApiException {
        String startDate = salesReportForm.getStartDate() + " 00:00:00";
        String endDate = salesReportForm.getEndDate() + " 23:59:59";

        ValidationUtil.validateForms(salesReportForm);
        SalesReportFormHelper.normalizeSalesReportForm(salesReportForm);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime sDate = LocalDateTime.parse(startDate, formatter);
        LocalDateTime eDate = LocalDateTime.parse(endDate, formatter);
        DateValidatorUtil.isValidDateTimeRange(sDate, eDate);

        List<OrderPojo> list = orderService.getOrderByDateFilter(sDate,eDate);
        return getFilterSalesReport(list,salesReportForm.getBrand(), salesReportForm.getCategory());
    }

    public List<SalesReportData> getFilterSalesReport(List<OrderPojo> list, String brand, String category)
            throws ApiException {
        // HashMap<BrandId, SalesReportData>
        HashMap<Integer, SalesReportData> map = new HashMap<Integer, SalesReportData>();
        // HashMap<ProductId, ProductPojo>
        HashMap<Integer, ProductPojo> productMap = getProductMap(list);
        // HashMap<BrandId, BrandPojo>
        HashMap<Integer, BrandPojo> brandMap = getBrandMap();


        for(OrderPojo orderPojo: list){
            List<OrderItemPojo> orderItemPojoList = orderService.getOrderItemsByOrderId(orderPojo.getId());
            calculateSalesReportData(map, productMap, brandMap, orderItemPojoList, brand, category);
        }

        salesList = SalesReportFormHelper.convertMaptoSalesList(map, brandMap);
        return salesList;
    }

    public void generateCsv(HttpServletResponse response) throws  IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"salesReport.csv\"");

        csvGenerator.writeSalesToCsv(salesList, response.getWriter());
        salesList.clear();
    }

    private HashMap<Integer, ProductPojo> getProductMap(List<OrderPojo> list) throws ApiException {
        HashMap<Integer, ProductPojo> productMap = new HashMap<>();
        Set<Integer> productIdList = new HashSet<>();
        for (OrderPojo orderPojo: list) {
            List<OrderItemPojo> orderItemPojoList = orderService.getOrderItemsByOrderId(orderPojo.getId());
            for (OrderItemPojo orderItemPojo: orderItemPojoList) {
                productIdList.add(orderItemPojo.getProductId());
            }
        }
        List<String> barcodes = new ArrayList<>();
        for (Integer productId: productIdList) {
            barcodes.add(productService.get(productId).getBarcode());
        }
        List<ProductPojo> productPojoList = productService.selectInBarcode(barcodes);
        for (ProductPojo productPojo: productPojoList) {
            productMap.put(productPojo.getId(), productPojo);
        }
        return productMap;
    }

    private HashMap<Integer, BrandPojo> getBrandMap()  {
        HashMap<Integer, BrandPojo> brandMap = new HashMap<>();
        List<BrandPojo> brandPojoList = brandService.getAll();
        for (BrandPojo brandPojo: brandPojoList) {
            brandMap.put(brandPojo.getId(), brandPojo);
        }
        return brandMap;
    }

    private void calculateSalesReportData(HashMap<Integer, SalesReportData> map, HashMap<Integer, ProductPojo> productMap
            , HashMap<Integer, BrandPojo> brandMap, List<OrderItemPojo> orderItemPojoList, String brand, String category) {

        DecimalFormat df = new DecimalFormat("#.##");
        for (OrderItemPojo orderItemPojo: orderItemPojoList) {
            ProductPojo productPojo = productMap.get(orderItemPojo.getProductId());
            BrandPojo brandPojo = brandMap.get(productPojo.getBrandCategory());
            if((Objects.equals(brand,brandPojo.getBrand()) || Objects.equals(brand,"all")) &&
                    (Objects.equals(category,brandPojo.getCategory()) || Objects.equals(category,"all"))) {
                if(!map.containsKey(brandPojo.getId())) {
                    map.put(brandPojo.getId(), new SalesReportData());
                }
                SalesReportData salesReportData = map.get(brandPojo.getId());
                salesReportData.setQuantity(salesReportData.getQuantity() + orderItemPojo.getQty() );
                salesReportData.setRevenue(Double.parseDouble(df.format(salesReportData.getRevenue()
                        + (orderItemPojo.getQty() * orderItemPojo.getSellingPrice()))));
            }
        }
    }
}
