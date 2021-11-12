package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.CashInventory;
import com.pharm.demo.web.data.services.base.CrudJpaService;

public interface CashInventoryService extends CrudJpaService<CashInventory, Long> {
    CashInventory findCashInventorByVersionNumber(Long inventoryVersionNumber);
}