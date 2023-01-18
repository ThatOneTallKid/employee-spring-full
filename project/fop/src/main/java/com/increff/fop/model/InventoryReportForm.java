package com.increff.fop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InventoryReportForm {
    private List<InventoryItem> inventoryDataList;
}
