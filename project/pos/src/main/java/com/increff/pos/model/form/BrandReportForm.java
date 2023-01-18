package com.increff.pos.model.form;

import com.increff.pos.model.data.BrandData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BrandReportForm {
    List<BrandData> brandItems;
}
