package com.increff.pos.model.data;

import com.increff.pos.model.form.InventoryForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryData extends InventoryForm {

    private Integer id;
    private String name;
    private String brand;
    private String category;

    private Double mrp;
}
