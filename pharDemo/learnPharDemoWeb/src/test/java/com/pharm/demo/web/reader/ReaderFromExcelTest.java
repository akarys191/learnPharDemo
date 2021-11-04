package com.pharm.demo.web.reader;

import com.pharm.demo.web.reader.dto.Remain;
import com.pharm.demo.web.reader.dto.RetailSystemRubus;
import com.pharm.demo.web.reader.service.ReaderFromExcel;
import com.pharm.demo.web.reader.service.impl.ReaderFromExcelImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
public class ReaderFromExcelTest {


    private ReaderFromExcel readerFromExcel;

    @Mock
    private Iterator<Cell> cellIterator ;

    @Mock
    private Iterator<Row> rowIterator ;

    @Mock
    private RetailSystemRubus retailSystemRubus;

    @Mock
    private Row row;

    @Before
    public void setUp() throws Exception {
        readerFromExcel = new ReaderFromExcelImpl();
    }

    @Test
    public void testConverting() {
        RetailSystemRubus retailSystemRubus = new RetailSystemRubus();
//   4600999001376	01.02.2024	 гель прокл для грудь СиЛ	1	1 уп 0 шт	1700	1700			Нет		105.6	testNum
        retailSystemRubus.setBarCode("4600999001376");
        retailSystemRubus.setSellBy(LocalDate.parse("01.02.2024", DateTimeFormatter.ofPattern("dd.MM.uuuu")));
        retailSystemRubus.setName(" гель прокл для грудь СиЛ");
        retailSystemRubus.setInStock(1.0);
        retailSystemRubus.setRemain(new Remain(1,0));
        retailSystemRubus.setPrice(1700.0);
        retailSystemRubus.setSum(1700.0);
        retailSystemRubus.setProducer("");
        retailSystemRubus.setProductGroup("");
        retailSystemRubus.setVAT(false);
        retailSystemRubus.setTVAND("");
        retailSystemRubus.setPc(105.6);
        retailSystemRubus.setRegistrationNumber("testNum");

            List<RetailSystemRubus> retailSystemList = readerFromExcel.converting("/excel/rubus_03.11.2021.xls");
            System.out.println(retailSystemList);
//            Assert.assertEquals("This not consist", retailSystemList.contains(retailSystemRubus) ,true);

    }


    @Test
    public void testCollectingActiveRetail() {


        RetailSystemRubus activeRetail = readerFromExcel.collectingActiveRetail(cellIterator);
        System.out.println(retailSystemRubus.getBarCode());
//        Assert.assertEquals(activeRetail, retailSystemRubus);
    }

    @Test
    public void testCheckIfFIleIsReadable(){
        String path = "123456";
        Assert.assertEquals(readerFromExcel.checkIfFIleIsReadable(path),false);
    }
    @Test
    public void testCheckIfRowIsEmpty(){
        Assert.assertEquals(readerFromExcel.checkIfRowIsEmpty(row),true);
    }

}