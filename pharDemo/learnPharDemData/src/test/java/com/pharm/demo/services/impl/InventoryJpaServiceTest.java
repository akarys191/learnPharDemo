package com.pharm.demo.services.impl;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.model.Medicine;
import com.pharm.demo.repositories.InventoryRepository;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@RunWith(SpringRunner.class)
public class InventoryJpaServiceTest {

    private InventoryJpaService inventoryJpaService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private Pageable pageable;





    @Before
    public void init() {
        this.inventoryJpaService = new InventoryJpaService(inventoryRepository);
    }


    @Test
    public void findInvoiceInventoryItemsByInventoryIdPaginated() {
        //given
        Long inventoryId = 123L;
        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryId);
        InvoiceInventoryItem invoiceInventoryItem = new InvoiceInventoryItem();
        invoiceInventoryItem.setInventory(inventory);
        List<InvoiceInventoryItem> list = new ArrayList<>();
        list.add(invoiceInventoryItem);
        Page<InvoiceInventoryItem> invoiceInventoryItemPage =  new PageImpl<>(list,pageable,list.size());

        when(inventoryRepository.findInvoiceInventoryItemsPaginated( pageable,inventoryId)).
                thenReturn(invoiceInventoryItemPage);

        //when
        Page<InvoiceInventoryItem> actualPage = inventoryJpaService.findInvoiceInventoryItemsByInventoryIdPaginated(pageable,inventoryId);
        Assert.assertEquals(actualPage,invoiceInventoryItemPage);
        Assert.assertEquals(actualPage.getContent().get(0).getInventory().getInventoryId(), inventoryId );
    }

    @Test
    public void latestInventoryVersionNumber() {
        Long latestInventory = 123L;

        when(inventoryRepository.findMaxInventoryVersionNumber()).thenReturn(latestInventory);
        Long target = inventoryJpaService.latestInventoryVersionNumber();

        Assert.assertEquals(target,
                latestInventory);

    }

    @Test
    public void findInventoryByVersionNumberAndMedicine() {
        //given
        Inventory inventory = new Inventory();
        Long versionId=123L;
        Long medicineId = 321L;
        inventory.setInventoryId(versionId);
        Medicine medicine = new Medicine();
        medicine.setId(medicineId);
        inventory.setMedicine(medicine);
        when(inventoryRepository.findInventoryByMedicine(versionId,medicineId)).thenReturn(inventory);
       //when
        Inventory inventoryByVersionNumberAndMedicine = inventoryJpaService.findInventoryByVersionNumberAndMedicine(versionId, medicineId);
        Assert.assertNotNull(inventoryByVersionNumberAndMedicine);
        Assert.assertEquals(inventoryByVersionNumberAndMedicine.getInventoryId(),versionId);
        Assert.assertEquals(inventoryByVersionNumberAndMedicine.getMedicine().getId(),medicineId);
    }
}