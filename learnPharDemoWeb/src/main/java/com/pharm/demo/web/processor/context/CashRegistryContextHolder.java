package com.pharm.demo.web.processor.context;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.model.CashRegistry;
import com.pharm.demo.services.CashRegistryService;
import org.springframework.stereotype.Component;

@Component
public class CashRegistryContextHolder {

    private CashRegistry currentCashRegistry;
    private CashRegistryService cashRegistryService;

    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    public CashRegistryContextHolder(InvoiceInventoryContextHolder invoiceInventoryContextHolder,
                                     CashRegistryService cashRegistryService) {
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
        this.cashRegistryService = cashRegistryService;
    }

    public CashRegistry getCashRegistryForToday() {
        if (currentCashRegistry == null) {
            currentCashRegistry = createCashRegistry();
        }
        return this.currentCashRegistry;
    }

    private CashRegistry createCashRegistry() {
        CashRegistry cashRegistry = new CashRegistry();
        cashRegistry.setInventoryVersionNumber(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber());
        CashInventory cashInventory = invoiceInventoryContextHolder.getActiveCashInventory();
        cashRegistry.setCashInventory(cashInventory);
        cashInventory.addCashRegistry(cashRegistry);
        return cashRegistryService.save(cashRegistry);
    }
}
