package com.pharm.demo.services.impl;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.repositories.CashInventoryRepository;
import com.pharm.demo.services.CashInventoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CashInventoryJpaServiceTest {

    private CashInventoryService cashInventoryService;

    @Mock
    private CashInventoryRepository cashInventoryRepository;


    @Before
    public void init(){
        this.cashInventoryService = new CashInventoryJpaService(cashInventoryRepository);
     }

    @Test
    public void findCashInventorByVersionNumber() {

        Long versionNumber = 213L;
        CashInventory cashInventory = new CashInventory();
        cashInventory.setCashInventoryId(versionNumber);

        //given
        when(cashInventoryRepository.findCashInventoryByInventoryVersionNumber(versionNumber)).thenReturn(cashInventory);


        //when
        CashInventory targetCashInventory = cashInventoryService.findCashInventorByVersionNumber(versionNumber);
        //then
        Assert.assertNotNull("this is Cash Inventory is Null",targetCashInventory);
        Assert.assertEquals("The version is not the same",cashInventory.getCashInventoryId(),targetCashInventory.getCashInventoryId());

    }

}