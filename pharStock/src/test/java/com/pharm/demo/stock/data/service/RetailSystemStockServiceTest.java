package com.pharm.demo.stock.data.service;

import com.pharm.demo.stock.data.exceptions.RetailSystemNotFound;
import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.data.repository.RetailSystemStockRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RetailSystemStockServiceTest {

    @InjectMocks
    private RetailSystemStockService retailSystemStockService;

    @Mock
    private RetailSystemStockRepository retailSystemRubusRepository;

    @Mock
    private List<RetailSystemStock> retailSystemStocks;

    @Test
    public void testListAll() {
        when(retailSystemRubusRepository.findAll()).thenReturn(retailSystemStocks);

        List<RetailSystemStock> actualRetailSystemStocks = retailSystemStockService.listAll();

        Assert.assertNotNull("The object is null", actualRetailSystemStocks);
        Assert.assertEquals(retailSystemStocks, actualRetailSystemStocks);
    }

    @Test
    public void testFindById() throws RetailSystemNotFound {
        BigInteger id = BigInteger.valueOf(123);
        RetailSystemStock retailSystemStock = new RetailSystemStock();
        retailSystemStock.set_id(id);

        when(retailSystemRubusRepository.findBy_id(id)).thenReturn(retailSystemStock);
        RetailSystemStock byId = retailSystemStockService.findById(id);

        Assert.assertNotNull("This is null", byId);
        Assert.assertEquals("They are not equal", id, byId.get_id());
    }
}