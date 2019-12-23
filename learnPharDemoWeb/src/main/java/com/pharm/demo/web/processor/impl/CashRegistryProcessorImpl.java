package com.pharm.demo.web.processor.impl;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.model.CashType;
import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.Sales;
import com.pharm.demo.services.CashInventoryService;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.SalesService;
import com.pharm.demo.web.processor.CashRegistryProcessor;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
public class CashRegistryProcessorImpl implements CashRegistryProcessor {

    private final InventoryService inventoryService;
    private final CashInventoryService cashInventoryService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;
    private final InventorySupplierLatestEditer inventorySupplierLatestEditer;
    private final SalesService salesService;
    private final ProcessorUtil processorUtil;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public CashRegistryProcessorImpl(InventoryService inventoryService, CashInventoryService cashInventoryService, InvoiceInventoryContextHolder invoiceInventoryContextHolder,
                                     InventorySupplierLatestEditer inventorySupplierLatestEditer, SalesService salesService, ProcessorUtil processorUtil) {
        this.inventoryService = inventoryService;
        this.cashInventoryService = cashInventoryService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
        this.inventorySupplierLatestEditer = inventorySupplierLatestEditer;
        this.salesService = salesService;
        this.processorUtil = processorUtil;
    }

    @Override
    public void processSaveCashRegistrySales(Sales cashRegistrySales) {
        Inventory currentInventory = getCurrentInventoryByMedicine(cashRegistrySales);
        cashRegistrySales.setInventory(currentInventory);
        inventorySupplierLatestEditer.editPriceSetCost(cashRegistrySales);
        if (Objects.isNull(cashRegistrySales.getSalesId()) && doesNotContainSales(cashRegistrySales)) {
            addSales(currentInventory, cashRegistrySales);
        } else {
            editSales(currentInventory, cashRegistrySales);
        }
        currentInventory.getSales().add(cashRegistrySales);
        salesService.save(cashRegistrySales);
        inventoryService.save(currentInventory);
    }

    @Override
    public void processDeleteCashRegistrySales(Sales cashRegistrySales) {
        Inventory currentInventory = getCurrentInventoryByMedicine(cashRegistrySales);
        currentInventory.getSales().removeIf(sales -> sales.getSalesId().equals(cashRegistrySales.getSalesId()));
        deleteInventorySales(currentInventory, cashRegistrySales);
        salesService.delete(cashRegistrySales);
        inventoryService.save(currentInventory);
    }

    private void addCashSales(Sales cashRegistrySales) {
        Objects.requireNonNull(cashRegistrySales.getCashRegistry());
        Objects.requireNonNull(cashRegistrySales.getCashRegistry().getCashInventory());

        CashInventory cashInventory = cashRegistrySales.getCashRegistry().getCashInventory();
        if (cashRegistrySales.getCashType() == CashType.CARD) {
            cashInventory.setTotalCardMoney(this.processorUtil.addNewValue(cashInventory.getTotalCardMoney(),
                    cashRegistrySales.getSoldSum()));
        } else {
            cashInventory.setTotalCashMoney(this.processorUtil.addNewValue(cashInventory.getTotalCashMoney(),
                    cashRegistrySales.getSoldSum()));
        }
        cashInventoryService.save(cashInventory);
    }

    private void editCashSalesWithSoldSum(Sales cashRegistrySales, Double existingSalesSum) {
        Objects.requireNonNull(cashRegistrySales.getCashRegistry());
        Objects.requireNonNull(cashRegistrySales.getCashRegistry().getCashInventory());

        CashInventory cashInventory = cashRegistrySales.getCashRegistry().getCashInventory();
        if (processorUtil.isOldValueChanged(existingSalesSum, cashRegistrySales.getSoldSum())) {
            if (cashRegistrySales.getCashType() == CashType.CARD) {
                cashInventory.setTotalCardMoney(this.processorUtil.reAddToTotal(existingSalesSum,
                        cashRegistrySales.getSoldSum(), cashInventory.getTotalCardMoney()));
            } else {
                cashInventory.setTotalCashMoney(this.processorUtil.reAddToTotal(existingSalesSum,
                        cashRegistrySales.getSoldSum(), cashInventory.getTotalCashMoney()));
            }
        }
        cashInventoryService.save(cashInventory);
    }

    private void addSales(Inventory currentInventory, Sales cashRegistrySales) {
        addCashSales(cashRegistrySales);
        addInventorySales(currentInventory, cashRegistrySales);
    }

    private void addInventorySales(Inventory currentInventory, Sales cashRegistrySales) {
        currentInventory.getSales().add(cashRegistrySales);
        currentInventory.setTotalSoldPriceSum(this.processorUtil.addNewValue(currentInventory.getTotalSoldPriceSum(), cashRegistrySales.getSoldSum()));
        currentInventory.setTotalSoldCost(this.processorUtil.addNewValue(currentInventory.getTotalSoldCost(), cashRegistrySales.getSoldCost()));
        currentInventory.setTotalSoldQuantity(this.processorUtil.addNewValue(currentInventory.getTotalSoldQuantity(), cashRegistrySales.getQuantity()));
    }

    private void editSales(Inventory currentInventory, Sales cashRegistrySales) {
        Sales existingCashRegistrySales = salesService.findById(cashRegistrySales.getSalesId());
        editCashSalesWithSoldSum(cashRegistrySales, existingCashRegistrySales.getSoldSum());
        editInventorySales(currentInventory, cashRegistrySales, existingCashRegistrySales);
    }

    private void editInventorySales(Inventory currentInventory, Sales cashRegistrySales, Sales existingCashRegistrySales) {
        if (this.processorUtil.isOldValueChanged(existingCashRegistrySales.getSoldSum(), cashRegistrySales.getSoldSum())) {
            currentInventory.setTotalSoldPriceSum(this.processorUtil.reAddToTotal(existingCashRegistrySales.getSoldSum(), cashRegistrySales.getSoldSum(),
                    currentInventory.getTotalBoughtPriceSum()));
        }
        if (this.processorUtil.isOldValueChanged(existingCashRegistrySales.getSoldCost(), cashRegistrySales.getSoldCost())) {
            currentInventory.setTotalSoldCost(this.processorUtil.reAddToTotal(existingCashRegistrySales.getSoldCost(), cashRegistrySales.getSoldCost(),
                    currentInventory.getTotalSoldCost()));
        }
        if (this.processorUtil.isOldValueChanged(existingCashRegistrySales.getQuantity(), cashRegistrySales.getQuantity())) {
            currentInventory.setTotalSoldQuantity(this.processorUtil.reAddToTotal(existingCashRegistrySales.getQuantity(), cashRegistrySales.getQuantity(),
                    currentInventory.getTotalSoldQuantity()));
        }
    }

    private void deleteInventorySales(Inventory currentInventory, Sales cashRegistrySales) {
        currentInventory.setTotalSoldPriceSum(currentInventory.getTotalSoldPriceSum() - cashRegistrySales.getSoldSum());
        currentInventory.setTotalSoldCost(currentInventory.getTotalSoldCost() - cashRegistrySales.getSoldCost());
        currentInventory.setTotalSoldQuantity(currentInventory.getTotalSoldQuantity() - cashRegistrySales.getQuantity());
    }

    private Inventory getCurrentInventoryByMedicine(Sales cashRegistrySales) {
        return inventoryService.findInventoryByVersionNumberAndMedicine(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(), cashRegistrySales.getMedicine().getId());
    }

    private boolean doesNotContainSales(Sales cashRegistrySales) {
        Objects.requireNonNull((cashRegistrySales));
        Objects.requireNonNull(cashRegistrySales.getCashRegistry());
        return !cashRegistrySales.getCashRegistry().getSales().contains(cashRegistrySales);
    }
}
