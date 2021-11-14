package com.pharm.demo.stock.data.service.reader.service.impl;

import com.pharm.demo.stock.data.model.Remain;
import com.pharm.demo.stock.data.service.reader.service.RemainderParser;

import org.junit.Assert;
import org.junit.Test;

public class RemainderParserImplTest {

    private RemainderParser inStockParser = new RemainderParserImpl();

    @Test
    public void testStringParserIntoInStock() {

        Remain inStock = new Remain(2, 10);


        Remain active = inStockParser.parserStringIntoInStock(inStock.getInPackage() + " уп " + inStock.getInPeace() + " шт");

        Assert.assertEquals("The parser not working", active, inStock);
    }
}