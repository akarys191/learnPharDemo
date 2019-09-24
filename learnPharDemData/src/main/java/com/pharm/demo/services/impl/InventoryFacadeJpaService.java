package com.pharm.demo.services.impl;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryFacadeService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("springdatajpa")
@Transactional
public class InventoryFacadeJpaService implements InvoiceInventoryFacadeService {


    private final InventoryService inventoryService;
    private final InvoiceInventoryItemService invoiceInventoryItemService;
    private final InvoiceInventoryService invoiceInventoryService;

    private Long currentInventoryId;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InventoryFacadeJpaService(InventoryService inventoryService, InvoiceInventoryItemService invoiceInventoryItemService,
                                     InvoiceInventoryService invoiceInventoryService) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryItemService = invoiceInventoryItemService;
        this.invoiceInventoryService = invoiceInventoryService;
    }

    @Override
    public void saveInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem) {
        if (invoiceInventoryItem.getInvoiceInventoryItemId() == null && !invoiceInventory.getInvoiceInventoryItems().contains(invoiceInventoryItem)) {
            invoiceInventory.getInvoiceInventoryItems().add(invoiceInventoryItem);
            invoiceInventoryItem.setInvoice(invoiceInventory);
            Inventory currentInventoryMedicine = (getCurrentInventoryByMedicine(invoiceInventoryItem));
            invoiceInventoryItem.setInventory(currentInventoryMedicine);
        }
        invoiceInventoryService.save(invoiceInventory);
        invoiceInventoryItemService.save(invoiceInventoryItem);
    }

    @Override
    public void deleteInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem deleteInventory) {
        invoiceInventory.getInvoiceInventoryItems().removeIf(inventory -> inventory.getInvoiceInventoryItemId().equals(deleteInventory.getInvoiceInventoryItemId()));
        invoiceInventoryItemService.delete(deleteInventory);
        invoiceInventoryService.save(invoiceInventory);
    }

    private Inventory getCurrentInventoryByMedicine(InvoiceInventoryItem invoiceInventoryItem) {
        if (currentInventoryId == null) {
            currentInventoryId = inventoryService.latestInventoryId();
        }
        return inventoryService.findInventoryByMedicine(currentInventoryId, invoiceInventoryItem.getMedicine().getId());
    }
}
