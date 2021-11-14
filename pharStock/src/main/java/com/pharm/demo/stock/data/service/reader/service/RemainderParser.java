package com.pharm.demo.stock.data.service.reader.service;


import com.pharm.demo.stock.data.model.Remain;

public interface RemainderParser {
    Remain parserStringIntoInStock(String s);
}
