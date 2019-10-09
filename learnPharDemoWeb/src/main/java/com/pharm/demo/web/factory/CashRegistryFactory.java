package com.pharm.demo.web.factory;

import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CashRegistryFactory {


    private final InventoryService inventoryService;
    private final InvoiceInventoryItemService invoiceInventoryItemService;
    private final InvoiceInventoryService invoiceInventoryService;

    private Long currentInventoryVersionNumber;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public CashRegistryFactory(InventoryService inventoryService, InvoiceInventoryItemService invoiceInventoryItemService,
                               InvoiceInventoryService invoiceInventoryService) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryItemService = invoiceInventoryItemService;
        this.invoiceInventoryService = invoiceInventoryService;
    }


}
