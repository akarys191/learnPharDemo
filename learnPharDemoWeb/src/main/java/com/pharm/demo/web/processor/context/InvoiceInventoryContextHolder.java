package com.pharm.demo.web.processor.context;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.services.CashInventoryService;
import com.pharm.demo.services.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class InvoiceInventoryContextHolder {

    private final InventoryService inventoryService;
    private final CashInventoryService cashInventoryService;

    private Long activeInventoryVersionNumber;
    private CashInventory activeCashInventory;


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InvoiceInventoryContextHolder(InventoryService inventoryService, CashInventoryService cashInventoryService) {
        this.inventoryService = inventoryService;
        this.cashInventoryService = cashInventoryService;
    }

    @PostConstruct
    public void initInitialInventoryContext() {
        this.activeInventoryVersionNumber = inventoryService.latestInventoryVersionNumber();
        if (Objects.isNull(this.activeInventoryVersionNumber)) {
            this.activeInventoryVersionNumber = 1L;
        }
        System.out.println(" this.activeInventoryVersionNumber ::: " + this.activeInventoryVersionNumber);
        this.activeCashInventory = cashInventoryService.findCashInventorByVersionNumber(activeInventoryVersionNumber);
    }

    public Long getActiveInvoiceInventoryVersionNumber() {
        return activeInventoryVersionNumber;
    }

    private void setActiveInventoryVersionNumber(Long inventoryVersionNumber) {
        this.activeInventoryVersionNumber = inventoryVersionNumber;
    }

    public CashInventory getActiveCashInventory() {
        return this.activeCashInventory;
    }
}
