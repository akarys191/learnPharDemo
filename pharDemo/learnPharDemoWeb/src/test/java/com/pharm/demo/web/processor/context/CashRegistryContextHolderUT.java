
package com.pharm.demo.web.processor.context;

import com.pharm.demo.web.data.model.CashInventory;
import com.pharm.demo.web.data.model.CashRegistry;
import com.pharm.demo.web.data.services.CashRegistryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CashRegistryContextHolderUT {

    @Mock
    InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    @Mock
    CashRegistryService cashRegistryService;

    @Test
    public void checkIfGetCashRegistryForToday() {
       // given
        CashInventory cashInventory = new CashInventory();
        cashInventory.setCashInventoryId(1L);
        String excludeFields = "";


        when(invoiceInventoryContextHolder.getActiveCashInventory()).thenReturn(cashInventory);
        when(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber()).thenReturn(1L);
        when(cashRegistryService.save(any())).thenAnswer(returnsFirstArg());

        CashRegistry targetCashRegistry = createTargetCashRegistry(cashInventory);
        CashRegistryContextHolder cashRegistryContextHolder = new CashRegistryContextHolder(invoiceInventoryContextHolder, cashRegistryService);

        System.out.println("??????????");
        System.out.println(cashRegistryContextHolder.getCashRegistryForToday().toString());
        System.out.println( new ReflectionEquals(targetCashRegistry.toString(), excludeFields));
        System.out.println("??????????");

        //when
         CashRegistry cashRegistryForToday = cashRegistryContextHolder.getCashRegistryForToday();

         //then
         Assert.assertTrue(new ReflectionEquals(targetCashRegistry, excludeFields).matches(cashRegistryForToday));
    }

    private CashRegistry createTargetCashRegistry(CashInventory cashInventory) {
        CashRegistry cashRegistry = new CashRegistry();
        cashRegistry.setInventoryVersionNumber(1L);
        cashRegistry.setCashInventory(cashInventory);
        cashInventory.addCashRegistry(cashRegistry);
        cashRegistry.setCashRegistryDate(LocalDate.now());
        return cashRegistry;
    }
}

