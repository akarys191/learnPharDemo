package com.pharm.demo.web.processor.context;

import com.pharm.demo.web.data.model.CashInventory;
import com.pharm.demo.web.data.model.CashRegistry;
import com.pharm.demo.web.data.services.CashRegistryService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

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
        if (currentCashRegistry == null || !isToday(currentCashRegistry.getCashRegistryDate())
                || currentCashRegistry.isClosed()) {
            currentCashRegistry = getExistingOrNewCashRegistryForToday();
        }
        return this.currentCashRegistry;
    }

    private CashRegistry getExistingOrNewCashRegistryForToday() {
        CashRegistry cashRegistryForToday = cashRegistryService.findLatestForToday();
        if (Objects.isNull(cashRegistryForToday) || cashRegistryForToday.isClosed()) {
            return createCashRegistryForToday();
        }
        return cashRegistryForToday;
    }

    private CashRegistry createCashRegistryForToday() {
        CashRegistry cashRegistry = new CashRegistry();
        cashRegistry.setInventoryVersionNumber(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber());
        CashInventory cashInventory = invoiceInventoryContextHolder.getActiveCashInventory();
        cashRegistry.setCashRegistryDate(LocalDate.now());
        cashRegistry.setCashInventory(cashInventory);
        if (Objects.isNull(cashInventory)) {
            throw new IllegalStateException(String.format("No cash inventory was found for version %s",
                    invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber()));
        }
        cashInventory.addCashRegistry(cashRegistry);
        return cashRegistryService.save(cashRegistry);
    }

    private boolean isToday(LocalDate date) {
        return LocalDate.now().compareTo(date) == 0;
    }
}
