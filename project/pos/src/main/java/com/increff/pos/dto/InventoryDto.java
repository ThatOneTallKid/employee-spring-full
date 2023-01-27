package com.increff.pos.dto;

import com.increff.pos.helper.InventoryFormHelper;
import com.increff.pos.model.data.InventoryData;
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
import com.increff.pos.util.ValidationUtil;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.pos.helper.InventoryFormHelper.convertInventoryFormToPojo;
import static com.increff.pos.helper.InventoryFormHelper.convertInventoryPojoToData;

@Component
public class InventoryDto {
    
    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;



    //TODO make add inventory list call
    public void add(InventoryForm form) throws ApiException {
        ValidationUtil.validateForms(form);
        InventoryPojo inventoryPojo = convertInventoryFormToPojo(form,productService.getIDByBarcode(form.getBarcode()));
        inventoryService.add(inventoryPojo);
    }

    public InventoryData get(int id) throws ApiException{
        InventoryPojo inventoryPojo = inventoryService.get(id);
        ProductPojo productPojo = productService.get(id);
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
        int p_id = productService.getIDByBarcode(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo = convertInventoryFormToPojo(inventoryForm,p_id);
        inventoryService.update(id,inventoryPojo);
    }

    public ResponseEntity<byte[]> getPDF() throws IOException, ApiException {
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

        InventoryReportForm inventoryReportForm = new InventoryReportForm();
        inventoryReportForm.setInventoryDataList(inventoryItemList);

        Path pdfPath = Paths.get("./Test/inventoryreport.pdf");

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8085/fop/api/inventoryreport";

        byte[] contents = restTemplate.postForEntity(url, inventoryReportForm, byte[].class).getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        return response;
    }
}
