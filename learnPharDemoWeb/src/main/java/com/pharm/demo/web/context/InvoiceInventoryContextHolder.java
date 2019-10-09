package com.pharm.demo.web.context;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class InvoiceInventoryContextHolder {


    private final InventoryService inventoryService;
    private final InvoiceInventoryItemService invoiceInventoryItemService;
    private final InvoiceInventoryService invoiceInventoryService;

    //TODO remove it and put it to separate context provider
    private Long currentInventoryVersionNumber;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InvoiceInventoryContextHolder(InventoryService inventoryService, InvoiceInventoryItemService invoiceInventoryItemService,
                                         InvoiceInventoryService invoiceInventoryService) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryItemService = invoiceInventoryItemService;
        this.invoiceInventoryService = invoiceInventoryService;
    }

    private Inventory getCurrentInventoryByMedicine(InvoiceInventoryItem invoiceInventoryItem) {
        if (currentInventoryVersionNumber == null) {
            currentInventoryVersionNumber = inventoryService.latestInventoryVersionNumber();
        }
        return inventoryService.findInventoryByVersionNumberAndMedicine(currentInventoryVersionNumber, invoiceInventoryItem.getMedicine().getId());
    }
}
