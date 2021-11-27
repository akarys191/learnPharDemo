package com.pharm.demo.stock.data.service.reader.service.impl;

import com.pharm.demo.stock.data.model.Remain;
import com.pharm.demo.stock.data.service.reader.service.RemainderParser;
import org.springframework.stereotype.Component;

@Component
public class RemainderParserImpl implements RemainderParser {
    @Override
    public Remain parserStringIntoInStock(String string) {
        Remain inStock = new Remain();
        inStock.setInPackage(Integer.valueOf(string.substring(0, string.indexOf("уп")).trim()));
        inStock.setInPeace(Integer.valueOf(string.substring(string.indexOf("уп") + 2, string.indexOf("шт")).trim()));
        return inStock;
    }
}
