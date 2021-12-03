package com.pharm.demo.stock.service.reader.service;


import com.pharm.demo.stock.data.model.Remain;

public interface RemainderParser {
    Remain parserStringIntoInStock(String s);
}
