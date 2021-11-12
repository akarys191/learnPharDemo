package com.pharm.demo.web.processor;

import com.pharm.demo.web.data.model.Sales;

public interface CashRegistryProcessor {
    void processSaveCashRegistrySales(Sales cashRegistrySales);

    void processDeleteCashRegistrySales(Sales cashRegistrySales);
}
