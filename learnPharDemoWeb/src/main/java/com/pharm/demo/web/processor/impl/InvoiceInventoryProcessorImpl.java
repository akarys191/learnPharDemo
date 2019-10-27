package com.pharm.demo.web.processor.impl;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InventorySupplierLatestPrice;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InventorySupplierPriceService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import com.pharm.demo.web.processor.InvoiceInventoryProcessor;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@Component
@Transactional
public class InvoiceInventoryProcessorImpl implements InvoiceInventoryProcessor {


    private final InventoryService inventoryService;
    private final InvoiceInventoryItemService invoiceInventoryItemService;
    private final InvoiceInventoryService invoiceInventoryService;
    private final InventorySupplierPriceService inventorySupplierPriceService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InvoiceInventoryProcessorImpl(InventoryService inventoryService, InvoiceInventoryItemService invoiceInventoryItemService,
                                         InvoiceInventoryService invoiceInventoryService, InvoiceInventoryContextHolder invoiceInventoryContextHolder,
                                         InventorySupplierPriceService inventorySupplierPriceService) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryItemService = invoiceInventoryItemService;
        this.invoiceInventoryService = invoiceInventoryService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
        this.inventorySupplierPriceService = inventorySupplierPriceService;
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
        return inventoryService.findInventoryByVersionNumberAndMedicine(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(), invoiceInventoryItem.getMedicine().getId());
    }

    private void addInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory) {
        invoiceInventory.getInvoiceInventoryItems().add(invoiceInventoryItem);
        invoiceInventoryItem.setInvoice(invoiceInventory);
        invoiceInventoryItem.setInventory(currentInventory);
        currentInventory.setInventorySupplierLatestPrices(Collections.singletonList(createInventorySupplierPrice(currentInventory, invoiceInventoryItem)));
        currentInventory.setTotalBoughtCost(addInventoryValue(currentInventory.getTotalBoughtCost(), invoiceInventoryItem.getPaidSum()));
        currentInventory.setTotalBoughtQuantity(addInventoryValue(currentInventory.getTotalBoughtQuantity(), invoiceInventoryItem.getQuantity()));
        currentInventory.setTotalBoughtPriceSum(addInventoryValue(currentInventory.getTotalBoughtPriceSum(), invoiceInventoryItem.getPriceSum()));
    }

    private void editInventory(InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory) {
        InvoiceInventoryItem existingInvoiceInventoryItem = invoiceInventoryItemService.findById(invoiceInventoryItem.getInvoiceInventoryItemId());
        editInventorySupplierPrice(currentInventory, invoiceInventoryItem);
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

    private InventorySupplierLatestPrice createInventorySupplierPrice(Inventory inventory, InvoiceInventoryItem invoiceInventoryItem) {
        InventorySupplierLatestPrice inventorySupplierLatestPrice = new InventorySupplierLatestPrice(inventory, invoiceInventoryItem.getSupplier(),
                invoiceInventoryItem.getPrice());
        return inventorySupplierPriceService.save(inventorySupplierLatestPrice);
    }

    private void editInventorySupplierPrice(Inventory inventory, InvoiceInventoryItem invoiceInventoryItem) {
        Objects.requireNonNull(inventory.getInventorySupplierLatestPrices());
        InventorySupplierLatestPrice inventorySupplierLatestPrice = inventorySupplierPriceService.findInventoryByInventoryAndSupplier(inventory.getInventoryId(), invoiceInventoryItem.getSupplier().getId());
        if (Objects.nonNull(inventorySupplierLatestPrice)) {
            inventorySupplierLatestPrice.setLatestPrice(invoiceInventoryItem.getPrice());
        } else {
            createInventorySupplierPrice(inventory, invoiceInventoryItem);
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
