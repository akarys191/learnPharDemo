package com.pharm.demo.web.processor.impl;

import com.pharm.demo.model.*;
import com.pharm.demo.services.CashInventoryService;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import com.pharm.demo.web.processor.InvoiceInventoryProcessor;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

@Component
@Transactional
public class InvoiceInventoryProcessorImpl implements InvoiceInventoryProcessor {

    private final InventoryService inventoryService;
    private final CashInventoryService cashInventoryService;
    private final InvoiceInventoryItemService invoiceInventoryItemService;
    private final InvoiceInventoryService invoiceInventoryService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;
    private final ProcessorUtil processorUtil;
    private final InventorySupplierLatestEditer inventorySupplierLatestEditer;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public InvoiceInventoryProcessorImpl(InventoryService inventoryService, CashInventoryService cashInventoryService, InvoiceInventoryItemService invoiceInventoryItemService,
                                         InvoiceInventoryService invoiceInventoryService, InventorySupplierLatestEditer inventorySupplierLatestPriceEditer,
                                         InvoiceInventoryContextHolder invoiceInventoryContextHolder, ProcessorUtil processorUti) {
        this.inventoryService = inventoryService;
        this.cashInventoryService = cashInventoryService;
        this.invoiceInventoryItemService = invoiceInventoryItemService;
        this.invoiceInventoryService = invoiceInventoryService;
        this.inventorySupplierLatestEditer = inventorySupplierLatestPriceEditer;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
        this.processorUtil = processorUti;
    }

    @Override
    public void processSaveInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem) {
        Inventory currentInventory = getCurrentInventoryByMedicine(invoiceInventoryItem);

        if (invoiceInventoryItem.getInvoiceInventoryItemId() == null && doesNotContainInventoryItem(invoiceInventory, invoiceInventoryItem)) {
            if (Objects.isNull(currentInventory)) {
                currentInventory = createInventoryForMedicine(invoiceInventoryItem.getMedicine());
            }
            addInvoiceInventoryItem(invoiceInventory, invoiceInventoryItem, currentInventory);
        } else {
            if (Objects.isNull(currentInventory)) {
                throw new IllegalStateException(String.format(
                        "Initial inventory for this %s medicine is absent!", invoiceInventoryItem.getMedicine().getId()));
            }
            editInvoiceInventoryItem(invoiceInventoryItem, currentInventory);
        }
        invoiceInventoryService.save(invoiceInventory);
        invoiceInventoryItemService.save(invoiceInventoryItem);
        inventoryService.save(currentInventory);
    }

    private boolean doesNotContainInventoryItem(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem) {
        Objects.requireNonNull(invoiceInventory);
        Objects.requireNonNull(invoiceInventory.getInvoiceInventoryItems());
        return !invoiceInventory.getInvoiceInventoryItems().contains(invoiceInventoryItem);
    }

    @Override
    public void processDeleteInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem deleteInventory) {
        invoiceInventory.getInvoiceInventoryItems().removeIf(inventory -> inventory.getInvoiceInventoryItemId().equals(deleteInventory.getInvoiceInventoryItemId()));
        Inventory currentInventory = getCurrentInventoryByMedicine(deleteInventory);
        deleteInventory(deleteInventory, currentInventory);
        invoiceInventoryItemService.delete(deleteInventory);
        invoiceInventoryService.save(invoiceInventory);
        inventoryService.save(currentInventory);
    }

    private Inventory getCurrentInventoryByMedicine(InvoiceInventoryItem invoiceInventoryItem) {
        return inventoryService.findInventoryByVersionNumberAndMedicine(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(), invoiceInventoryItem.getMedicine().getId());
    }

    private void addInvoiceInventoryItem(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory) {
        CashInventory cashInventory = invoiceInventoryContextHolder.getActiveCashInventory();

        if (isEnoughTotalMoney(invoiceInventoryItem, cashInventory)) {
            currentInventory.setInventorySupplierLatestPriceCosts(
                    new ArrayList<>(Collections.singletonList(inventorySupplierLatestEditer.createNew(currentInventory, invoiceInventoryItem))));
            addInventory(invoiceInventory, invoiceInventoryItem, currentInventory);
            addCashInventory(invoiceInventoryItem, cashInventory);
        } else {
            throw new IllegalStateException(
                    String.format("Not enough money to buy the %s for invItem %s with total money %s in CashInventory",
                            invoiceInventoryItem.getMedicine().getBarCode(), invoiceInventoryItem.getInvoiceInventoryItemId(),
                            cashInventory.getTotalMoney()));
        }
        cashInventoryService.save(cashInventory);
    }

    private boolean isEnoughTotalMoney(InvoiceInventoryItem invoiceInventoryItem, CashInventory cashInventory) {
        return cashInventory.getTotalMoney() >= invoiceInventoryItem.getSuppliedCost();
    }

    private void addCashInventory(InvoiceInventoryItem invoiceInventoryItem, CashInventory cashInventory) {
        if (cashInventory.getTotalCashMoney() >= invoiceInventoryItem.getPaidSum()) {
            cashInventory.setTotalCashMoney(this.processorUtil.deductNewValue(cashInventory.getTotalCashMoney(),
                    invoiceInventoryItem.getPaidSum()));
        } else if (cashInventory.getTotalCardMoney() >= invoiceInventoryItem.getPaidSum()) {
            cashInventory.setTotalCardMoney(this.processorUtil.deductNewValue(cashInventory.getTotalCardMoney(),
                    invoiceInventoryItem.getPaidSum()));
        } else {
            cashInventory.setTotalCashMoney(0D);
            cashInventory.setTotalCardMoney(0D);
        }
    }

    private void addInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory) {
        invoiceInventory.getInvoiceInventoryItems().add(invoiceInventoryItem);
        invoiceInventoryItem.setInvoice(invoiceInventory);
        invoiceInventoryItem.setInventory(currentInventory);
        currentInventory.setTotalBoughtCost(this.processorUtil.addNewValue(currentInventory.getTotalBoughtCost(), invoiceInventoryItem.getPaidSum()));
        currentInventory.setTotalBoughtQuantity(this.processorUtil.addNewValue(currentInventory.getTotalBoughtQuantity(), invoiceInventoryItem.getQuantity()));
        currentInventory.setTotalBoughtPriceSum(this.processorUtil.addNewValue(currentInventory.getTotalBoughtPriceSum(), invoiceInventoryItem.getPriceSum()));
    }

    private void editInvoiceInventoryItem(InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory) {
        CashInventory cashInventory = invoiceInventoryContextHolder.getActiveCashInventory();
        InvoiceInventoryItem existingInvoiceInventoryItem = invoiceInventoryItemService.findById(invoiceInventoryItem.getInvoiceInventoryItemId());

        if ((cashInventory.getTotalMoney() + existingInvoiceInventoryItem.getPaidSum()) >= invoiceInventoryItem.getPaidSum()) {
            inventorySupplierLatestEditer.edit(currentInventory, invoiceInventoryItem);
            editCashInventory(invoiceInventoryItem, cashInventory, existingInvoiceInventoryItem);
            editInventory(invoiceInventoryItem, currentInventory, existingInvoiceInventoryItem);
        } else {
            throw new IllegalStateException(
                    String.format("Not enough money to re-buy the %s for invItem %s with total money %s in CashInventory",
                            invoiceInventoryItem.getMedicine().getBarCode(), invoiceInventoryItem.getInvoiceInventoryItemId(),
                            cashInventory.getTotalMoney()));
        }
        cashInventoryService.save(cashInventory);
    }

    private void editCashInventory(InvoiceInventoryItem invoiceInventoryItem, CashInventory cashInventory, InvoiceInventoryItem existingInvoiceInventoryItem) {
        if ((cashInventory.getTotalCashMoney() + existingInvoiceInventoryItem.getSuppliedCost()) >= invoiceInventoryItem.getPaidSum()) {
            cashInventory.setTotalCashMoney(this.processorUtil.reDeductToTotal(existingInvoiceInventoryItem.getPaidSum(),
                    invoiceInventoryItem.getSuppliedCost(), cashInventory.getTotalCashMoney()));
        } else if ((cashInventory.getTotalCardMoney() + existingInvoiceInventoryItem.getSuppliedCost()) >= invoiceInventoryItem.getSuppliedCost()) {
            cashInventory.setTotalCardMoney(this.processorUtil.reDeductToTotal(existingInvoiceInventoryItem.getSuppliedCost(),
                    invoiceInventoryItem.getPaidSum(), cashInventory.getTotalCardMoney()));
        } else {
            cashInventory.setTotalCashMoney(0D);
            cashInventory.setTotalCardMoney(0D);
        }
    }

    private void editInventory(InvoiceInventoryItem invoiceInventoryItem, Inventory currentInventory, InvoiceInventoryItem existingInvoiceInventoryItem) {
        if (this.processorUtil.isOldValueChanged(existingInvoiceInventoryItem.getPaidSum(), invoiceInventoryItem.getPaidSum())) {
            currentInventory.setTotalBoughtCost(this.processorUtil.reAddToTotal(existingInvoiceInventoryItem.getPaidSum(), invoiceInventoryItem.getPaidSum(),
                    currentInventory.getTotalBoughtCost()));
        }
        if (this.processorUtil.isOldValueChanged(existingInvoiceInventoryItem.getQuantity(), invoiceInventoryItem.getQuantity())) {
            currentInventory.setTotalBoughtQuantity(this.processorUtil.reAddToTotal(existingInvoiceInventoryItem.getQuantity(), invoiceInventoryItem.getQuantity(),
                    currentInventory.getTotalBoughtQuantity()));
        }
        if (this.processorUtil.isOldValueChanged(existingInvoiceInventoryItem.getPriceSum(), invoiceInventoryItem.getPriceSum())) {
            currentInventory.setTotalBoughtPriceSum(this.processorUtil.reAddToTotal(existingInvoiceInventoryItem.getPriceSum(), invoiceInventoryItem.getPriceSum(),
                    currentInventory.getTotalBoughtPriceSum()));
        }
    }

    private void deleteInventory(InvoiceInventoryItem deleteInventoryItem, Inventory currentInventory) {
        currentInventory.setTotalBoughtCost(currentInventory.getTotalBoughtCost() - deleteInventoryItem.getPaidSum());
        currentInventory.setTotalBoughtQuantity(currentInventory.getTotalBoughtQuantity() - deleteInventoryItem.getQuantity());
        currentInventory.setTotalBoughtPriceSum(currentInventory.getTotalBoughtPriceSum() - deleteInventoryItem.getPriceSum());
    }

    private Inventory createInventoryForMedicine(Medicine medicine) {
        Objects.requireNonNull(medicine);
        Inventory newInventory = new Inventory(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(),
                medicine);
        return inventoryService.saveFlush(newInventory);
    }
}
