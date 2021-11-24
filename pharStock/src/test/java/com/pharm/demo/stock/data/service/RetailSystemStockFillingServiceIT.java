package com.pharm.demo.stock.data.service;

import com.mongodb.Mongo;
import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.data.repository.RetailSystemStockRepository;
import com.pharm.demo.stock.data.service.crawler.BasicWebCrawler;
import com.pharm.demo.stock.data.service.reader.service.ReaderFromExcel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Ignore
public class RetailSystemStockFillingServiceIT {

    @Value("${rubus_url}")
    private String url;

    @Mock
    private ReaderFromExcel reader;

    @Autowired
    private RetailSystemStockFillingService retailSystemStockFillingService;
    @Autowired
    private RetailSystemStockRepository retailSystemRubusRepository;

    @Mock
    private BasicWebCrawler webCrawler;

    @Mock
    private File file;

    @Mock
    private List<RetailSystemStock> retailSystemStocks;

    @Before
    public void setUp() throws Exception {
        when(webCrawler.getStockFile(url)).thenReturn(file);
        when(reader.convertIntoObject(file)).thenReturn(retailSystemStocks);
    }


    @Test
    @Ignore
    public void testSaveRubusStock() throws IOException {
        retailSystemStockFillingService.saveRubusStock();
        List<RetailSystemStock> all = retailSystemRubusRepository.findAll();
        Assert.assertEquals("This not cool, they are not equal",retailSystemStocks,all);
    }
}