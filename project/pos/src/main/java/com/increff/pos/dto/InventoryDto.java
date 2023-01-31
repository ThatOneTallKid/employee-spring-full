package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.pos.helper.InventoryFormHelper;
import com.increff.pos.model.data.BrandErrorData;
import com.increff.pos.model.data.InventoryData;
import com.increff.pos.model.data.InventoryErrorData;
import com.increff.pos.model.data.InventoryItem;
import com.increff.pos.model.form.InventoryForm;
import com.increff.pos.model.form.InventoryReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertUtil;
import com.increff.pos.util.CsvFileGenerator;
import com.increff.pos.util.ErrorUtil;
import com.increff.pos.util.ValidationUtil;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.RollbackException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.pos.helper.InventoryFormHelper.*;

@Component
public class InventoryDto {
    
    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    @Autowired
    CsvFileGenerator csvGenerator;


    public void add(List<InventoryForm> forms) throws ApiException, JsonProcessingException {
        List<InventoryErrorData> inventoryErrorDataList = new ArrayList<>();
        inventoryErrorDataList.clear();
        int errorSize = 0;
        for (InventoryForm form: forms) {
            InventoryErrorData inventoryErrorData = ConvertUtil.convert(form, InventoryErrorData.class);
            inventoryErrorData.setMessage("");
            try {
                ValidationUtil.validateForms(form);
                normalizeInventory(form);
                ProductPojo productPojo = productService.getCheck(productService.getIDByBarcode(form.getBarcode()));
            }
            catch (ApiException e) {
                inventoryErrorData.setMessage(e.getMessage());
                errorSize++;
            }
            inventoryErrorDataList.add(inventoryErrorData);
        }

        if(errorSize > 0) {
            ErrorUtil.throwErrors(inventoryErrorDataList);
        }
            bulkAdd(forms);
    }


    public InventoryData get(int id) throws ApiException{
        InventoryPojo inventoryPojo = inventoryService.CheckIdInventory(id);
        ProductPojo productPojo = productService.get(id);
        BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
        return convertInventoryPojoToData(inventoryPojo, productPojo.getBarcode(), productPojo.getName(), brandPojo);
    }

    public InventoryData getByBarcode(String barcode) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.getById(productService.getIDByBarcode(barcode));
        ProductPojo productPojo = productService.get(productService.getIDByBarcode(barcode));
        BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
        return convertInventoryPojoToData(inventoryPojo, productPojo.getBarcode(), productPojo.getName(), brandPojo);
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<>();
        for(InventoryPojo inventoryPojo : list) {
            ProductPojo productPojo = productService.get(inventoryPojo.getId());
            BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
            list2.add(convertInventoryPojoToData(inventoryPojo,productPojo.getBarcode(), productPojo.getName(), brandPojo));
        }
        return list2;
    }

    public void update(int id, InventoryForm inventoryForm) throws ApiException {
        ValidationUtil.validateForms(inventoryForm);
        int pId = productService.getIDByBarcode(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo = convertInventoryFormToPojo(inventoryForm,pId);
        inventoryService.update(id,inventoryPojo);
    }

    public void generateCsv(HttpServletResponse response) throws IOException, ApiException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"inventoryReport.csv\"");

        csvGenerator.writeInventoryToCsv(getAllItem(), response.getWriter());
    }

    public List<InventoryItem> getAllItem() throws ApiException {
        List<InventoryData> inventoryDataList = getAll();
        List<InventoryItem> inventoryItemList = new ArrayList<>();

        HashMap<Pair<String, String>, Integer> map = new HashMap<>();
        for(InventoryData inventoryData : inventoryDataList) {
            BrandPojo brandPojo = brandService.getCheck(productService.get(inventoryData.getId()).getBrandCategory());
            Pair<String, String> pair= new Pair<>(brandPojo.getBrand(), brandPojo.getCategory());
            if(map.containsKey(pair)) {
                int prev = map.get(pair);
                map.replace(pair, prev+inventoryData.getQty());
            }
            else {
                map.put(pair,inventoryData.getQty());
            }
        }

        for(Map.Entry<Pair<String,String>, Integer> mapElement : map.entrySet()) {
            inventoryItemList.add(InventoryFormHelper.convertMapToItem(mapElement));
        }

        return inventoryItemList;
    }

    @Transactional(rollbackOn = ApiException.class)
    private void bulkAdd(List<InventoryForm> forms) throws ApiException {
        for(InventoryForm form: forms) {
            InventoryPojo inventoryPojo = convertInventoryFormToPojo(form, productService.getIDByBarcode(form.getBarcode()));
            inventoryService.add(inventoryPojo);
        }
    }
}
