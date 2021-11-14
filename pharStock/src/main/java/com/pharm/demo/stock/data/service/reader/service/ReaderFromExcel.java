package com.pharm.demo.stock.data.service.reader.service;

import com.pharm.demo.stock.data.model.RetailSystemRubus;
import com.pharm.demo.stock.data.service.reader.exceptions.UnsupportedFormatExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ReaderFromExcel{
    boolean checkIfFIleIsReadable(File file);
    Workbook openFile(File file) throws IOException, UnsupportedFormatExcel;
    List<RetailSystemRubus> convertIntoObject(File file);
    RetailSystemRubus collectingActiveRetail(Row row);
    boolean isRowEmpty(Row row);
    }
