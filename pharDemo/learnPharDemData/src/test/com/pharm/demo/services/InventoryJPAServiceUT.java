package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.Inventory;
import com.pharm.demo.web.data.model.InvoiceInventoryItem;
import com.pharm.demo.web.data.repositories.InventoryRepository;
import com.pharm.demo.web.data.services.impl.InventoryJpaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class InventoryJPAServiceUT {

    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;


    @Mock
    private Page<Inventory> inventoryPage;

    @Mock
    private Inventory inventory;

    @Mock
    private Page<InvoiceInventoryItem> invoiceInventoryItemPage;

    @Mock
    private Pageable pageable;

    @Before
    public void init(){
        this.inventoryService = new InventoryJpaService(this.inventoryRepository);
    }


    @Test
    public void checkIfFindPaginated() {
        //given
        when(inventoryRepository.findAll(pageable)).thenReturn(inventoryPage);

        //when
        Page actualPage = this.inventoryService.findPaginated(pageable);

        //then
        Assert.assertNotNull("actual page is null", actualPage);
        Assert.assertEquals("actual page is not what expected", inventoryPage, actualPage);
    }

    @Test
    public void checkIfFindInvoiceInventoryItemsByInventoryIdPaginated(){
        //given
        long inventoryId = 1L;
        when(this.inventoryRepository.findInvoiceInventoryItemsPaginated(pageable , inventoryId)).thenReturn(invoiceInventoryItemPage);

        //when
        Page actualPage = this.inventoryService.findInvoiceInventoryItemsByInventoryIdPaginated(pageable, inventoryId);

        //then
        Assert.assertNotNull("actual page is null", actualPage);
        Assert.assertEquals("actual page is not what expected", invoiceInventoryItemPage, actualPage);
    }

    @Test
    public void checkLatestInventoryVersionNumber(){
        //given
        long expectedVersionNumber = 1234L;
        when(this.inventoryRepository.findMaxInventoryVersionNumber()).thenReturn(expectedVersionNumber);

        //when
        Long latestInventoryVersion = inventoryService.latestInventoryVersionNumber();

        //then
        Assert.assertNotNull(latestInventoryVersion);
        Assert.assertEquals("latest version number is not as expected", expectedVersionNumber, (long)latestInventoryVersion);

    }

}

