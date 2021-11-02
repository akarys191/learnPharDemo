package com.pharm.demo.web.processor;

import com.pharm.demo.model.Sales;

public interface CashRegistryProcessor {
    void processSaveCashRegistrySales(Sales cashRegistrySales);

    void processDeleteCashRegistrySales(Sales cashRegistrySales);
}
