package com.increff.pos.dto;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.data.BrandErrorData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.ConvertUtil;
import com.increff.pos.util.CsvFileGenerator;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.BrandFormHelper.*;


@Component
public class BrandDto {
    @Autowired
    BrandService brandService;

    @Autowired
    private CsvFileGenerator csvGenerator;

    public List<BrandErrorData> add(List<BrandForm> brandForms) throws ApiException {
        List<BrandErrorData> errorData= new ArrayList<>();
        for(BrandForm brandForm: brandForms) {
            try{
                ValidationUtil.validateForms(brandForm);
                normalizeBrand(brandForm);
                BrandPojo b = convertBrandFormToPojo(brandForm);
                brandService.add(b);
            }
            catch (ApiException e) {
                BrandErrorData brandErrorData = ConvertUtil.convert(brandForm, BrandErrorData.class);
                brandErrorData.setMessage(e.getMessage());
                errorData.add(brandErrorData);
            }
        }

        return errorData;
    }

    public BrandData get(int id) throws ApiException{
        return convertBrandPojoToData(brandService.getCheck(id));
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<>();
        for(BrandPojo b : list) {
            list2.add(convertBrandPojoToData(b));
        }
        return list2;
    }

    public void update(int id, BrandForm brandForm) throws ApiException {
        ValidationUtil.validateForms(brandForm);
        normalizeBrand(brandForm);
        BrandPojo p = convertBrandFormToPojo(brandForm);
        brandService.update(id,p);
    }

    public void generateCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"brandReport.csv\"");
        csvGenerator.writeBrandsToCsv(brandService.getAll(), response.getWriter());
    }

}
