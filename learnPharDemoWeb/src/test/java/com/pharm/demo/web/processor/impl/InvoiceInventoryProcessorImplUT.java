/*
package com.pharm.demo.web.processor.impl;

import com.pharm.demo.model.*;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class InvoiceInventoryProcessorImplUT {

    @Mock
    private InventoryService inventoryService;
    @Mock
    private InvoiceInventoryItemService invoiceInventoryItemService;
    @Mock
    private InvoiceInventoryService invoiceInventoryService;
    @Mock
    private InvoiceInventoryContextHolder invoiceInventoryContextHolder;
    @Spy
    private InvoiceInventory invoiceInventory;

    private InvoiceInventoryProcessorImpl invoiceInventoryProcessor;

    @Before
    public void initInvoiceInventoryProcessor() {
        System.out.println("Testing the processor");
        invoiceInventory = spy(new InvoiceInventory());
        mockServiceContextMethods();
        invoiceInventoryProcessor = new InvoiceInventoryProcessorImpl(inventoryService, invoiceInventoryItemService, invoiceInventoryService, invoiceInventoryContextHolder);
    }

    @Test
    public void testProcessSaveAddInventory() {
        Medicine medicine = createDefaultMedicine();
        Inventory inventory = createDefaultInventory(medicine);
        InvoiceInventoryItem invoiceInventoryItem = createDefaultInvoiceInventoryItem(medicine);

        when(inventoryService.findInventoryByVersionNumberAndMedicine(
                invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(), medicine.getId())).thenReturn(inventory);
        invoiceInventoryProcessor.processSaveInventory(invoiceInventory, invoiceInventoryItem);

        assertInvoiceInventoryItemWithInventory(inventory, null, invoiceInventoryItem);
    }

    @Test
    public void testProcessSaveEditInventory() {
        Medicine medicine = createDefaultMedicine();
        Inventory expectedInventory = createDefaultInventory(medicine);
        InvoiceInventoryItem existingInvoiceInventoryItem = createDefaultInvoiceInventoryItem(medicine);
        InvoiceInventoryItem updatedInvoiceInventoryItem = createDefaultInvoiceInventoryItem(medicine);

        updatedInvoiceInventoryItem.setSuppliedCost(50D);
        updatedInvoiceInventoryItem.setPrice(50D);
        Inventory existingInventory = createDefaultInventory(medicine);

        updatedInvoiceInventoryItem.setInvoice(invoiceInventory);
        invoiceInventory.getInvoiceInventoryItems().add(updatedInvoiceInventoryItem);

        existingInventory.setTotalBoughtCost(100D);
        existingInventory.setTotalBoughtPriceSum(100D);
        existingInventory.setTotalBoughtQuantity(1D);

        expectedInventory.setTotalBoughtCost(50D);
        expectedInventory.setTotalBoughtPriceSum(50D);
        expectedInventory.setTotalBoughtQuantity(1D);

        when(invoiceInventoryItemService.findById(existingInvoiceInventoryItem.getInvoiceInventoryItemId())).
                thenReturn(existingInvoiceInventoryItem);

        when(inventoryService.findInventoryByVersionNumberAndMedicine(
                invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(), medicine.getId())).
                thenReturn(existingInventory);

        invoiceInventoryProcessor.processSaveInventory(invoiceInventory, updatedInvoiceInventoryItem);

        assertInvoiceInventoryItemWithInventory(expectedInventory, existingInventory, updatedInvoiceInventoryItem);
    }

    private void mockServiceContextMethods() {
        CashInventory cashInventory = new CashInventory();
        when(invoiceInventoryContextHolder.getActiveCashInventory()).thenReturn(cashInventory);
        when(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber()).thenReturn(1L);
        when(invoiceInventoryService.save(any())).thenAnswer(returnsFirstArg());
        when(invoiceInventoryItemService.save(any())).thenAnswer(returnsFirstArg());
        doReturn(new ArrayList<>()).when(invoiceInventory).getInvoiceInventoryItems();
    }

    public void assertInvoiceInventoryItemWithInventory(Inventory expectedInventory, Inventory defaultInventory,
                                                        InvoiceInventoryItem invoiceInventoryItem) {
        assertEquals(invoiceInventoryItem.getInvoice(), invoiceInventory);
        assertTrue(invoiceInventory.getInvoiceInventoryItems().contains(invoiceInventoryItem));
        assertEquals(expectedInventory.getTotalBoughtCost(), getOriginalValue(defaultInventory, Inventory::getTotalBoughtCost, invoiceInventoryItem.getPaidSum()));
        assertEquals(expectedInventory.getTotalBoughtPriceSum(), getOriginalValue(defaultInventory, Inventory::getTotalBoughtPriceSum, invoiceInventoryItem.getPriceSum()));
        assertEquals(expectedInventory.getTotalBoughtQuantity(), getOriginalValue(defaultInventory, Inventory::getTotalBoughtQuantity, invoiceInventoryItem.getQuantity()));
    }

    private Inventory createDefaultInventory(Medicine medicine) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setInventoryVersionNumber(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber());
        inventory.setMedicine(medicine);
        inventory.setTotalBoughtCost(0D);
        inventory.setTotalBoughtPriceSum(0D);
        inventory.setTotalBoughtQuantity(0D);
        return inventory;
    }

    private Medicine createDefaultMedicine() {
        Medicine medicine = new Medicine();
        medicine.setId(1L);
        return medicine;
    }

    private InvoiceInventoryItem createDefaultInvoiceInventoryItem(Medicine medicine) {
        InvoiceInventoryItem invoiceInventoryItem = new InvoiceInventoryItem();
        invoiceInventoryItem.setMedicine(medicine);
        invoiceInventoryItem.setQuantity(1D);
        invoiceInventoryItem.setSuppliedCost(100D);
        invoiceInventoryItem.setPrice(100D);
        return invoiceInventoryItem;
    }

    private Double getOriginalValue(Inventory defaultInventory, Function<Inventory, Double> funcRef, Double addValue) {
        return Optional.ofNullable(defaultInventory).map(funcRef).orElse(addValue);
    }
}
*/
