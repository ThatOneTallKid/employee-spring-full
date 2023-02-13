package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.data.BrandErrorData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.ConvertUtil;
import com.increff.pos.util.CsvFileGenerator;
import com.increff.pos.util.ErrorUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.dtoUtil.BrandFormHelper.*;


@Component
public class BrandDto {
    @Autowired
    private BrandService brandService;

    @Autowired
    private CsvFileGenerator csvGenerator;

    public void add(List<BrandForm> brandForms) throws ApiException, JsonProcessingException {
        List<BrandErrorData> errorData = new ArrayList<>();
        Integer errorSize = 0;
        for (BrandForm brandForm : brandForms) {
            BrandErrorData brandErrorData= ConvertUtil.convert(brandForm, BrandErrorData.class);
            brandErrorData.setMessage("");
            try {
                ValidationUtil.validateForms(brandForm);
                normalizeBrand(brandForm);
                brandService.checkBrandExists(brandForm.getBrand(), brandForm.getCategory());

            } catch (Exception e) {
                errorSize++;
                brandErrorData.setMessage(e.getMessage());
            }
            errorData.add(brandErrorData);
        }
        if (errorSize > 0) {
            ErrorUtil.throwErrors(errorData);
        }

        bulkAdd(brandForms);

    }

    public BrandData get(Integer id) throws ApiException{
        return convertBrandPojoToData(brandService.getCheck(id));
    }

    public List<BrandData> getAll() {
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandData> brandDataList = new ArrayList<>();
        for(BrandPojo b : brandPojoList) {
            brandDataList.add(convertBrandPojoToData(b));
        }
        return brandDataList;
    }

    public void update(Integer id, BrandForm brandForm) throws ApiException {
        ValidationUtil.validateForms(brandForm);
        normalizeBrand(brandForm);
        BrandPojo brandPojo = convertBrandFormToPojo(brandForm);
        brandService.update(id,brandPojo);
    }


    public void generateCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"brandReport.csv\"");
        csvGenerator.writeBrandsToCsv(brandService.getAll(), response.getWriter());
    }

    @Transactional(rollbackOn = ApiException.class)
    private void bulkAdd(List<BrandForm> brandForms) throws ApiException {
        for (BrandForm brandForm: brandForms){
            BrandPojo b = convertBrandFormToPojo(brandForm);
            brandService.add(b);
        }
    }

}
