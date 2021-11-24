package com.pharm.demo.stock.data.service.reader;


import com.pharm.demo.stock.data.model.Remain;
import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.data.service.reader.service.RemainderParser;
import com.pharm.demo.stock.data.service.reader.service.impl.ReaderFromExcelImpl;
import com.pharm.demo.stock.data.service.reader.service.impl.RemainderParserImpl;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


@RunWith(SpringRunner.class)
public class ReaderFromExcelImplTest {

    private ReaderFromExcelImpl readerFromExcelImpl;

    @Mock
    private Row row;

    private RemainderParser remainderParser;

    @Before
    public void setUp() {
        remainderParser = new RemainderParserImpl();
        readerFromExcelImpl = new ReaderFromExcelImpl(remainderParser);
    }

    @Test
    public void testConverting() {
        RetailSystemStock retailSystemStock = new RetailSystemStock();
        retailSystemStock.setBarCode("4600999001376");
        retailSystemStock.setSellByDate(LocalDate.parse("01.02.2024", DateTimeFormatter.ofPattern("dd.MM.uuuu")));
        retailSystemStock.setName(" гель прокл для грудь СиЛ");
        retailSystemStock.setInStock(1.0);
        retailSystemStock.setRemain(new Remain(1, 0));
        retailSystemStock.setPrice(1700.0);
        retailSystemStock.setSum(1700.0);
        retailSystemStock.setProducer("");
        retailSystemStock.setProductGroup("");
        retailSystemStock.setVAT(false);
        retailSystemStock.setTVAND("");
        retailSystemStock.setPc(105.6);
        retailSystemStock.setRegistrationNumber("testNum");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("excel/rubus_test_case_1.xls")).getFile());
        List<RetailSystemStock> retailSystemList = readerFromExcelImpl.convertIntoObject(file);
        System.out.println(retailSystemList);
        Assert.assertTrue("This not consist", retailSystemList.contains(retailSystemStock));
    }

    @Test
    public void testCheckIfFIleIsReadable(){
        File file = new File("file","xls");
        Assert.assertFalse(readerFromExcelImpl.checkIfFIleIsReadable(file));
    }
    @Test
    public void testCheckIfRowIsEmpty(){
        Assert.assertTrue(readerFromExcelImpl.isRowEmpty(row));
    }

}