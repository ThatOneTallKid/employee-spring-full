package com.increff.pos.dto;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.model.form.BrandReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.pos.helper.BrandFormHelper.*;


@Component
public class BrandDto {
    @Autowired
    BrandService brandService;

    public void add(BrandForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        BrandPojo b = convertBrandFormToPojo(f);
        normalizeBrand(b);
        if(Objects.isNull(brandService.getBrandPojofromBrandCategory(b.getBrand(), b.getCategory()))){
            brandService.add(b);
        }
        else {
            throw new ApiException("Brand and Category already exists !");
        }
    }

    public BrandData get(int id) throws ApiException{
        return convertBrandPojoToData(brandService.get(id));
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<>();
        for(BrandPojo b : list) {
            list2.add(convertBrandPojoToData(b));
        }
        return list2;
    }

    public void update(int id, BrandForm f) throws ApiException {
        ValidationUtil.validateForms(f);
        BrandPojo p = convertBrandFormToPojo(f);
        normalizeBrand(p);
        brandService.update(id,p);
    }

    public ResponseEntity<byte[]> getPDF() throws Exception{
        System.out.println("here");
        List<BrandData> brandItems= getAll();
        BrandReportForm brandReportForm = new BrandReportForm();
        brandReportForm.setBrandItems(brandItems);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8085/fop/api/brandreport";
        byte[] contents = restTemplate.postForEntity(url, brandReportForm, byte[].class).getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "brandreport.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

}
