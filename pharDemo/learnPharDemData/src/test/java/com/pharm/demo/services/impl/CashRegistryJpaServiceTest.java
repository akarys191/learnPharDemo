package com.pharm.demo.services.impl;

import com.pharm.demo.model.CashRegistry;
import com.pharm.demo.repositories.CashRegistryRepository;
import com.pharm.demo.services.CashRegistryService;
import org.apache.catalina.LifecycleState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class CashRegistryJpaServiceTest {

    private CashRegistryService cashRegistryService;

    @Mock
    private CashRegistryRepository cashRegistryRepository;


    @Before
    public void init(){
        this.cashRegistryService = new CashRegistryJpaService(cashRegistryRepository);
        //given
//        when(cashRegistryService.findLatestForToday()).thenReturn(cashRegistry);
    }

    @Test
    public void findLatestForToday() {
        List<CashRegistry> list = new ArrayList<>();
        CashRegistry cashRegistry = new CashRegistry();
        cashRegistry.setCashRegistryDate(LocalDate.now());
        list.add(cashRegistry);
        when(cashRegistryRepository.findAllForDate(LocalDate.now())).thenReturn(list);

        //when
        CashRegistry latestForToday = cashRegistryService.findLatestForToday();


        //then

        Assert.assertNotNull("it is null", latestForToday);
        Assert.assertEquals("They are not same", cashRegistry, latestForToday);

    }
}