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
import java.util.List;

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

    public void add(InventoryForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        InventoryPojo b = convertInventoryFormToPojo(f,productService.getIDByBarcode(f.getBarcode()) );
        inventoryService.add(b);
    }

    public InventoryData get(int id) throws ApiException{
        InventoryPojo i = inventoryService.get(id);
        ProductPojo p = productService.get(id);
        return convertInventoryPojoToData(i, p.getBarcode(), p.getName());
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<>();
        for(InventoryPojo b : list) {
            ProductPojo p = productService.get(b.getId());
            list2.add(convertInventoryPojoToData(b,p.getBarcode(), p.getName()));
        }
        return list2;
    }

    public void update(int id, InventoryForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        int p_id = productService.getIDByBarcode(f.getBarcode());
        InventoryPojo p = convertInventoryFormToPojo(f,p_id);
        inventoryService.update(id,p);
    }

    public ResponseEntity<byte[]> getPDF() throws IOException, ApiException {
        List<InventoryData> inventoryDataList = getAll();
        List<InventoryItem> inventoryItemList = new ArrayList<>();
        for(InventoryData i : inventoryDataList) {
            ProductPojo p = productService.get(i.getId());
            BrandPojo b = brandService.get(p.getBrandCategory());
            InventoryItem inventoryItem = InventoryFormHelper.convertInventoryDataToItem(i, b.getBrand(), b.getCategory());
            inventoryItemList.add(inventoryItem);
        }
        InventoryReportForm inventoryReportForm = new InventoryReportForm();
        inventoryReportForm.setInventoryDataList(inventoryItemList);
        Path pdfPath = Paths.get("./Test/inventoryreport.pdf");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8085/fop/api/inventoryreport";

        byte[] contents = restTemplate.postForEntity(url, inventoryReportForm, byte[].class).getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
}
