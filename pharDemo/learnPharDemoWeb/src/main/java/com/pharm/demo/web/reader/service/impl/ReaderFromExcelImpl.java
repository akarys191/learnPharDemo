package com.pharm.demo.web.reader.service.impl;


import com.pharm.demo.web.reader.dto.RetailSystemRubus;
import com.pharm.demo.web.reader.exceptions.UnsupportedFormatExcel;
import com.pharm.demo.web.reader.service.ReaderFromExcel;
import com.pharm.demo.web.reader.service.RemainderParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class ReaderFromExcelImpl implements ReaderFromExcel {

    private final RemainderParser remainderParser;

    @Autowired
    public ReaderFromExcelImpl(RemainderParser remainderParser) {
        this.remainderParser = remainderParser;
    }

    public boolean checkIfFIleIsReadable(String s) {
        return s.contains(".xls") && !s.contains(".xlsx");
    }

    public HSSFWorkbook openFile(String path) throws IOException, URISyntaxException, UnsupportedFormatExcel {
        if (checkIfFIleIsReadable(path)) {
            URL resource = getClass().getResource(path);
            if (resource!=null) {
                return new HSSFWorkbook(new FileInputStream(new File(resource.toURI())));
            }else throw new FileNotFoundException("Cannot load this file");
        }else throw new UnsupportedFormatExcel("Incorrect type of you file, please change it",path);
        }

    public List<RetailSystemRubus> convertIntoObject(String path) {
        List<RetailSystemRubus> retailSystemRubuses =  new ArrayList<>();
        try {
            boolean checkForFindingBarCode = false;
            HSSFWorkbook myExcelBook = openFile(path);
            HSSFSheet sheet = myExcelBook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                    if (!isRowEmpty(row)) {
                        if(!checkForFindingBarCode) {
                           checkForFindingBarCode = findRowOfBarCode(row);
                        }
                        if (checkForFindingBarCode){
                        retailSystemRubuses.add(collectingActiveRetail(row)); }
                    }
            }
        } catch (IOException | URISyntaxException | UnsupportedFormatExcel e) {
            log.error("There is error in parsing of xls file",e);
        }
        return retailSystemRubuses;
    }

    public RetailSystemRubus collectingActiveRetail(Row row) {
        Iterator<Cell> cellIterator = row.iterator();
        RetailSystemRubus activeRubus = new RetailSystemRubus();
        while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
            int activeColumn = cell.getAddress().getColumn();
            switch (activeColumn) {
                //
                case 0:
                        activeRubus.setBarCode(cell.getStringCellValue());
                    break;
                case 1:
                    if (!cell.getStringCellValue().equals(""))
                        activeRubus.setSellByDate(LocalDate.parse(cell.getStringCellValue(), DateTimeFormatter.ofPattern("dd.MM.uuuu")));
                    else activeRubus.setSellByDate(LocalDate.MIN);
                    break;
                case 2:
                        activeRubus.setName(cell.getStringCellValue());
                    break;
                case 3:
                    activeRubus.setInStock( cell.getNumericCellValue());
                    break;
                case 4:
                        activeRubus.setRemain(remainderParser.parserStringIntoInStock( cell.getStringCellValue()));
                    break;
                case 5:
                    activeRubus.setPrice(cell.getNumericCellValue());
                    break;
                case 6:
                    activeRubus.setSum(cell.getNumericCellValue());
                    break;
                case 7:
                    activeRubus.setProducer(cell.getStringCellValue());
                    break;
                case 8:
                    activeRubus.setProductGroup(cell.getStringCellValue());
                    break;
                case 9:
                    if (cell.getStringCellValue().contains("да")) activeRubus.setVAT(true);
                    break;
                case 10:
                        activeRubus.setTVAND(cell.getStringCellValue());
                    break;
                case 11:
                    activeRubus.setPc(cell.getNumericCellValue());
                    break;
                case 12:
                    activeRubus.setRegistrationNumber(cell.getStringCellValue());
                    break;
                default:
            }
        }
        return activeRubus;
    }

    public boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }
    public Boolean findRowOfBarCode(Row row){

        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getCellTypeEnum() == CellType.STRING)
            {
                String stringCellValue = cell.getStringCellValue();
                if (stringCellValue.equals("Штрихкод")) return true;
            }
        }
    return false;
    }



}
