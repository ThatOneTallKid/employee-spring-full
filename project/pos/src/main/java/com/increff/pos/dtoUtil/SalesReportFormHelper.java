package com.increff.pos.dtoUtil;

import com.increff.pos.model.data.SalesReportData;
import com.increff.pos.model.form.SalesReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReportFormHelper {
    public static void normalizeSalesReportForm(SalesReportForm salesReportForm) {
        salesReportForm.setBrand(StringUtil.toLowerCase(salesReportForm.getBrand()).trim());
        salesReportForm.setCategory(StringUtil.toLowerCase(salesReportForm.getCategory()).trim());
    }

    public static List<SalesReportData> convertMaptoSalesList(HashMap<Integer, SalesReportData> map,HashMap<Integer, BrandPojo> brandMap) {
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        for(Map.Entry<Integer, SalesReportData> entry: map.entrySet()) {
            BrandPojo bp = brandMap.get(entry.getKey());
            SalesReportData d = entry.getValue();
            d.setBrand(bp.getBrand());
            d.setCategory(bp.getCategory());
            salesReportDataList.add(d);
        }
        return salesReportDataList;
    }
}
