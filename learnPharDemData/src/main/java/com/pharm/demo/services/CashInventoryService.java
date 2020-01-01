package com.pharm.demo.services;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.services.base.CrudJpaService;

public interface CashInventoryService extends CrudJpaService<CashInventory, Long> {
    CashInventory findCashInventorByVersionNumber(Long inventoryVersionNumber);
}