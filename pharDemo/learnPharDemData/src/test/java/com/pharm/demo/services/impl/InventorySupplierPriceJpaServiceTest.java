package com.pharm.demo.services.impl;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InventorySupplierPriceCost;
import com.pharm.demo.model.Supplier;
import com.pharm.demo.repositories.InventorySupplierPriceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class InventorySupplierPriceJpaServiceTest {

    private InventorySupplierPriceJpaService inventorySupplierPriceJpaService;

    @Mock
    private InventorySupplierPriceRepository inventorySupplierPriceRepository;

    @Mock
    private List<InventorySupplierPriceCost> inventorySupplierPriceCostList;



    @Before
    public void setUp() throws Exception {
        this.inventorySupplierPriceJpaService = new InventorySupplierPriceJpaService(inventorySupplierPriceRepository);
    }

    @Test
    public void findPaginated() {
//        Page<InventorySupplierPriceCost> inventorySupplierPriceCosts = new PageImpl<>(inventorySupplierPriceCostList, pageable,inventorySupplierPriceCostList.size());
//
//        when(inventorySupplierPriceRepository.findAll()).thenReturn(inventorySupplierPriceCostList);
//        Page<InventorySupplierPriceCost> paginated = inventorySupplierPriceJpaService.findPaginated(pageable);
//        Assert.assertNotNull("It's null",paginated);
//        Assert.assertEquals("they are not same",paginated.getContent(),inventorySupplierPriceCostList);
    }

    @Test
    public void findLatestInventoryByInventoryAndSupplier() {
        Long inventoryId = 456L;
        Long supplierId = 123L;

        InventorySupplierPriceCost inventorySupplierPriceCost = new InventorySupplierPriceCost();
        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryId);
        inventorySupplierPriceCost.setInventory(inventory);
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        inventorySupplierPriceCost.setSupplier(supplier);
        List<InventorySupplierPriceCost> inventorySupplierPriceCostArrayList = new ArrayList<>();
        inventorySupplierPriceCostArrayList.add(inventorySupplierPriceCost);
        Page<InventorySupplierPriceCost> pagedSuppliers = new PageImpl<>(inventorySupplierPriceCostArrayList,new PageRequest(0, 1),inventorySupplierPriceCostArrayList.size());

        when(inventorySupplierPriceRepository.findInventorySupplierPriceByInventorySupplierLatest(new PageRequest(0, 1),inventoryId,supplierId)).thenReturn(pagedSuppliers);

        InventorySupplierPriceCost latestInventoryByInventoryAndSupplier = inventorySupplierPriceJpaService.findLatestInventoryByInventoryAndSupplier(inventoryId, supplierId);
        Assert.assertEquals(latestInventoryByInventoryAndSupplier.getInventory().getInventoryId(),inventoryId);
        Assert.assertEquals(latestInventoryByInventoryAndSupplier.getSupplier().getId(),supplierId);
    }



}