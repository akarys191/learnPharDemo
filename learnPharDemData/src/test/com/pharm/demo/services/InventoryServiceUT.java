package com.pharm.demo.services;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class InventoryServiceUT {

    @Mock
    InventoryService inventoryService;


    @Mock
    Page<Inventory> inventoryPage;

    @Mock
    Inventory inventory;

    @Mock
    Page<InvoiceInventoryItem> invoiceInventoryItemPage;

    @Mock
    Pageable pageable;

    @Test
    public void checkIfFindPaginated() {
        when(inventoryService.findPaginated(pageable)).thenReturn(inventoryPage);
    }

    @Test
    public void checkIfFindInvoiceInventoryItemsByInventoryIdPaginated(){
        when(inventoryService.findInvoiceInventoryItemsByInventoryIdPaginated(pageable ,1L)).thenReturn(invoiceInventoryItemPage);
    }

    @Test
    public void checkLatestInventoryVersionNumber(){
        when(inventoryService.latestInventoryVersionNumber()).thenReturn(1234L);

        Assert.assertTrue((inventoryService.latestInventoryVersionNumber())==1234L);

    }

}

