package com.pharm.demo.web.reader.service;

import com.pharm.demo.web.reader.dto.RetailSystemRubus;
import com.pharm.demo.web.reader.exceptions.UnsupportedFormatExcel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

public interface ReaderFromExcel{
    Boolean checkIfFIleIsReadable(String s);
    Workbook openFile(String path) throws IOException, URISyntaxException, UnsupportedFormatExcel;
    List<RetailSystemRubus> converting(String path);
    RetailSystemRubus collectingActiveRetail(Iterator<Cell> cellIterator);
    Boolean checkIfRowIsEmpty(Row row);
    }
