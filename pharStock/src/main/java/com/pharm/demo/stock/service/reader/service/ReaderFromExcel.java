package com.pharm.demo.stock.service.reader.service;

import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.service.reader.exceptions.UnsupportedFormatExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ReaderFromExcel{
    boolean checkIfFIleIsReadable(File file);
    Workbook openFile(File file) throws IOException, UnsupportedFormatExcel;
    List<RetailSystemStock> convertIntoObject(File file);
    RetailSystemStock collectingActiveRetail(Row row);
    boolean isRowEmpty(Row row);
    }
