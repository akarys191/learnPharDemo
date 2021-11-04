package com.pharm.demo.web.reader.service;

import com.pharm.demo.web.reader.dto.Remain;

public interface RemainderParser {
    Remain StringParserIntoInStock(String s);
}
