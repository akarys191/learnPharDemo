package com.pharm.demo.web.processor.context;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.model.CashRegistry;
import com.pharm.demo.services.CashRegistryService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CashRegistryContextHolder {

    private CashRegistry currentCashRegistry;
    private CashRegistryService cashRegistryService;

    private InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    public CashRegistryContextHolder(InvoiceInventoryContextHolder invoiceInventoryContextHolder,
                                     CashRegistryService cashRegistryService) {
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
        this.cashRegistryService = cashRegistryService;
    }


    public CashRegistry getCashRegistryForToday() {
        if (currentCashRegistry == null || !isToday(currentCashRegistry.getCashRegistryDate())) {
            currentCashRegistry = createCashRegistryForToday();
        }
        return this.currentCashRegistry;
    }

    private CashRegistry createCashRegistryForToday() {
        CashRegistry cashRegistry = new CashRegistry();
        cashRegistry.setInventoryVersionNumber(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber());
        CashInventory cashInventory = invoiceInventoryContextHolder.getActiveCashInventory();
        cashRegistry.setCashRegistryDate(LocalDate.now());
        cashRegistry.setCashInventory(cashInventory);
        cashInventory.addCashRegistry(cashRegistry);
        return cashRegistryService.save(cashRegistry);
    }

    private boolean isToday(LocalDate date) {
        return LocalDate.now().compareTo(date) == 0;
    }
}
