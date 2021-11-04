package com.pharm.demo.web.reader.service.impl;


import com.pharm.demo.web.reader.dto.RetailSystemRubus;
import com.pharm.demo.web.reader.exceptions.UnsupportedFormatExcel;
import com.pharm.demo.web.reader.service.RemainderParser;
import com.pharm.demo.web.reader.service.ReaderFromExcel;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReaderFromExcelImpl implements ReaderFromExcel {

    private RemainderParser remainderParser;

    public ReaderFromExcelImpl() {
        remainderParser = new RemainderParserImpl();
    }

    public Boolean checkIfFIleIsReadable(String s) {
        if (s.contains(".xls") && !s.contains(".xlsx"))
            return true;
        else return false;
    }

    public HSSFWorkbook openFile(String path) throws IOException, URISyntaxException, UnsupportedFormatExcel {
        if (checkIfFIleIsReadable(path)){
            URL resource = getClass().getResource(path);
            if (resource!=null) {
                return new HSSFWorkbook(new FileInputStream(new File(resource.toURI())));
                //найменование пойск
            }else throw new FileNotFoundException("Cannot load this file");
        }else throw new UnsupportedFormatExcel("Incorrect type of you file, please change it",path);
        }

    public List<RetailSystemRubus> converting(String path) {
        List<RetailSystemRubus> retailSystemRubuses =  new ArrayList<>();;
        try {
            boolean checkForFindingBarCode = false;
            HSSFWorkbook myExcelBook = openFile(path);
            HSSFSheet sheet = myExcelBook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                    if (!checkIfRowIsEmpty(row)) {
                        if(!checkForFindingBarCode) {
                           checkForFindingBarCode = findRowOfBarCode(row);
                           continue;

                        }
                        if (checkForFindingBarCode)
                        retailSystemRubuses.add(collectingActiveRetail(row.cellIterator()));
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedFormatExcel e) {
            e.printStackTrace();
        }
        return retailSystemRubuses;
    }

    public RetailSystemRubus collectingActiveRetail(Iterator<Cell> cellIterator) {
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
                    if (cell.getStringCellValue() != "")
                        activeRubus.setSellBy(LocalDate.parse(cell.getStringCellValue(), DateTimeFormatter.ofPattern("dd.MM.uuuu")));
                    else activeRubus.setSellBy(LocalDate.MIN);
                    break;
                case 2:
                        activeRubus.setName(cell.getStringCellValue());
                    break;
                case 3:
                    activeRubus.setInStock( cell.getNumericCellValue());
                    break;
                case 4:
                        activeRubus.setRemain(remainderParser.StringParserIntoInStock( cell.getStringCellValue()));
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
                    else activeRubus.setVAT(false);
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
            }
        }
        return activeRubus;
    }

    public Boolean checkIfRowIsEmpty(Row row) {
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
