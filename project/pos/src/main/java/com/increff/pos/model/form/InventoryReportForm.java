package com.increff.pos.model.form;

import com.increff.pos.model.data.InventoryItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InventoryReportForm {
    private List<InventoryItem> inventoryDataList;
}
