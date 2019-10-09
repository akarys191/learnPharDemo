package com.pharm.demo.web.processor;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
public class InvoiceInventoryProcessorImpl implements InvoiceInventoryProcessor {


    private final InventoryService inventoryService;
    private final InvoiceInventoryItemService invoiceInventoryItemService;
    private final InvoiceInventoryService invoiceInventoryService;

    //TODO remove it and put it to separate context provider
    private Long currentInventoryVersionNumber;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InvoiceInventoryProcessorImpl(InventoryService inventoryService, InvoiceInventoryItemService invoiceInventoryItemService,
                                         InvoiceInventoryService invoiceInventoryService) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryItemService = invoiceInventoryItemService;
        this.invoiceInventoryService = invoiceInventoryService;
    }

    @Override
    public void processSaveInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem) {
        Inventory currentInventory = getCurrentInventoryByMedicine(invoiceInventoryItem);
        if (invoiceInventoryItem.getInvoiceInventoryItemId() == null && !invoiceInventory.getInvoiceInventoryItems().contains(invoiceInventoryItem)) {
            addInventory(invoiceInventory, invoiceInventoryItem, currentInventory);
        } else {
            editInventory(invoiceInventoryItem, currentInventory);
        }
        invoiceInventoryService.save(invoiceInventory);
        invoiceInventoryItemService.save(invoiceInventoryItem);
        inventoryService.save(currentInventory);
    }

    @Override
    public void processDeleteInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem deleteInventory) {
        invoiceInventory.getInvoiceInventoryItems().removeIf(inventory -> inventory.getInvoiceInventoryItemId().equals(deleteInventory.getInvoiceInventoryItemId()));
        invoiceInventoryItemService.delete(deleteInventory);
        invoiceInventoryService.save(invoiceInventory);
    }

    private Inventory getCurrentInventoryByMedicine(InvoiceInventoryItem invoiceInventoryItem) {
        if (currentInventoryVersionNumber == null) {
            currentInventoryVersionNumber = inventoryService.latestInventoryVersionNumber();
        }
        return inventoryService.findInventoryByVersionNumberAndMedicine(currentInventoryVersionNumber, invoiceInventoryItem.getMedicine().getId());
    }

    private void addInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory) {
        invoiceInventory.getInvoiceInventoryItems().add(invoiceInventoryItem);
        invoiceInventoryItem.setInvoice(invoiceInventory);
        invoiceInventoryItem.setInventory(currentInventory);
        currentInventory.setTotalBoughtCost(addInventoryValue(currentInventory.getTotalBoughtCost(), invoiceInventoryItem.getPaidSum()));
        currentInventory.setTotalBoughtQuantity(addInventoryValue(currentInventory.getTotalBoughtQuantity(), invoiceInventoryItem.getQuantity()));
        currentInventory.setTotalBoughtPriceSum(addInventoryValue(currentInventory.getTotalBoughtPriceSum(), invoiceInventoryItem.getPriceSum()));
    }

    private void editInventory(InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory) {
        InvoiceInventoryItem existingInvoiceInventoryItem = invoiceInventoryItemService.findById(invoiceInventoryItem.getInvoiceInventoryItemId());
        if (isOldValueChanged(existingInvoiceInventoryItem.getPaidSum(), invoiceInventoryItem.getPaidSum())) {
            currentInventory.setTotalBoughtCost(recalculateTotal(existingInvoiceInventoryItem.getPaidSum(), invoiceInventoryItem.getPaidSum(),
                    currentInventory.getTotalBoughtCost()));
        }
        if (isOldValueChanged(existingInvoiceInventoryItem.getQuantity(), invoiceInventoryItem.getQuantity())) {
            currentInventory.setTotalBoughtQuantity(recalculateTotal(existingInvoiceInventoryItem.getQuantity(), invoiceInventoryItem.getQuantity(),
                    currentInventory.getTotalBoughtQuantity()));
        }
        if (isOldValueChanged(existingInvoiceInventoryItem.getPriceSum(), invoiceInventoryItem.getPriceSum())) {
            currentInventory.setTotalBoughtPriceSum(recalculateTotal(existingInvoiceInventoryItem.getPriceSum(), invoiceInventoryItem.getPriceSum(),
                    currentInventory.getTotalBoughtPriceSum()));
        }
    }

    private boolean isOldValueChanged(Double existingValue, Double newValue) {
        Objects.requireNonNull(existingValue);
        return !existingValue.equals(newValue);
    }

    private Double recalculateTotal(Double existingValue, Double newValue, Double totalExistingValue) {
        Objects.requireNonNull(totalExistingValue);
        Objects.requireNonNull(existingValue);
        return totalExistingValue - existingValue + newValue;
    }

    private Double addInventoryValue(Double existingValue, Double newValue) {
        return existingValue + newValue;
    }
}
