package com.pharm.demo.web.processor.impl;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InventorySupplierPriceCost;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.model.Sales;
import com.pharm.demo.services.InventorySupplierLatestService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InventorySupplierLatestEditer {
    private final InventorySupplierLatestService inventorySupplierPriceService;
    private final ProcessorUtil processorUtil;

    public InventorySupplierLatestEditer(InventorySupplierLatestService inventorySupplierPriceService, ProcessorUtil processorUtil) {
        this.inventorySupplierPriceService = inventorySupplierPriceService;
        this.processorUtil = processorUtil;
    }

    public void edit(Inventory inventory, InvoiceInventoryItem invoiceInventoryItem) {
        Objects.requireNonNull(invoiceInventoryItem.getSupplier());
        InventorySupplierPriceCost inventorySupplierLatest = inventorySupplierPriceService.findLatestInventoryByInventoryAndSupplier(inventory.getInventoryId(), invoiceInventoryItem.getSupplier().getId());
        if (Objects.isNull(inventorySupplierLatest) || hasChangedPriceCost(invoiceInventoryItem.getPrice(), invoiceInventoryItem.getSuppliedCost(), inventorySupplierLatest)) {
            createNew(inventory, invoiceInventoryItem);
        }
    }

    public void editPriceSetCost(Sales cashRegistrySales) {
        Objects.requireNonNull(cashRegistrySales.getInventory());
        Objects.requireNonNull(cashRegistrySales.getSupplier());
        Objects.requireNonNull(cashRegistrySales.getPrice());
        InventorySupplierPriceCost inventorySupplierLatest = inventorySupplierPriceService.findLatestInventoryByInventoryAndSupplier(cashRegistrySales.getInventory().getInventoryId(), cashRegistrySales.getSupplier().getId());
        if (Objects.isNull(inventorySupplierLatest)) {
            throw new IllegalStateException("inventorySupplierLatest should not be null as it should be linked to invoiceInventoryItem");
        }
        cashRegistrySales.setSoldCost(inventorySupplierLatest.getCost());
        if (this.processorUtil.isOldValueChanged(inventorySupplierLatest.getPrice(), cashRegistrySales.getPrice())) {
            createNew(cashRegistrySales.getInventory(), cashRegistrySales);
        }

    }

    private boolean hasChangedPriceCost(Double latestPrice, Double latestCost, InventorySupplierPriceCost inventorySupplierLatest) {
        return Objects.nonNull(inventorySupplierLatest) && (this.processorUtil.isOldValueChanged(inventorySupplierLatest.getCost(), latestCost) || this.processorUtil.isOldValueChanged(inventorySupplierLatest.getPrice(), latestPrice));
    }

    public InventorySupplierPriceCost createNew(Inventory inventory, InvoiceInventoryItem invoiceInventoryItem) {
        return inventorySupplierPriceService.save(buildNew(inventory, invoiceInventoryItem));
    }

    public InventorySupplierPriceCost createNew(Inventory inventory, Sales cashRegistrySales) {
        return inventorySupplierPriceService.save(buildNew(inventory, cashRegistrySales));
    }

    private InventorySupplierPriceCost buildNew(Inventory inventory, InvoiceInventoryItem invoiceInventoryItem) {
        return InventorySupplierPriceCost.builder()
                .withInventory(inventory)
                .withInvoiceInventoryItemSupplierPriceCost(invoiceInventoryItem)
                .build();
    }

    private InventorySupplierPriceCost buildNew(Inventory inventory, Sales cashRegistrySales) {
        return InventorySupplierPriceCost.builder()
                .withInventory(inventory)
                .withSalesSupplierPriceCost(cashRegistrySales)
                .build();
    }
}