package com.pharm.demo.services;

import com.pharm.demo.model.CashInventory;

public interface CashInventoryService extends CrudService<CashInventory, Long> {
    public CashInventory findCashInventorByVersionNumber(Long inventoryVersionNumber);
}