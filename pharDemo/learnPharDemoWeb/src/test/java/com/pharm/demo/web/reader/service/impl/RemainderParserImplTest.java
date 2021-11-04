package com.pharm.demo.web.reader.service.impl;

import com.pharm.demo.web.reader.dto.Remain;
import com.pharm.demo.web.reader.service.RemainderParser;
import org.junit.Assert;
import org.junit.Test;

public class RemainderParserImplTest {

    private RemainderParser inStockParser = new RemainderParserImpl();

    @Test
    public void testStringParserIntoInStock() {

        Remain inStock = new Remain(2, 10);


        Remain active = inStockParser.StringParserIntoInStock(inStock.getInPackage() + " уп " + inStock.getInPeace() + " шт");

        Assert.assertEquals("The parser not working", active, inStock);
    }
}