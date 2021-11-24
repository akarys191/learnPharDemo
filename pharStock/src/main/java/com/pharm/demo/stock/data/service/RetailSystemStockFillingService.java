package com.pharm.demo.stock.data.service;

import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.data.repository.RetailSystemStockRepository;
import com.pharm.demo.stock.data.service.crawler.BasicWebCrawler;
import com.pharm.demo.stock.data.service.reader.service.ReaderFromExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class RetailSystemStockFillingService {

    private final RetailSystemStockRepository retailSystemRubusRepository;
    private final ReaderFromExcel reader;
    private final BasicWebCrawler webCrawler;

    public RetailSystemStockFillingService(RetailSystemStockRepository retailSystemRubusRepository, ReaderFromExcel reader, BasicWebCrawler webCrawler) {
        this.retailSystemRubusRepository = retailSystemRubusRepository;
        this.reader = reader;
        this.webCrawler = webCrawler;
    }

    public void saveRubusStock() throws IllegalStateException, IOException {

            List<RetailSystemStock> listOfRetailSystemStockFromFile = getListOfRetailSystemRubusFromFile();
            retailSystemRubusRepository.saveAll(listOfRetailSystemStockFromFile);

    }
    private File getFileFromWeb() throws IOException {
        // todo add to property
           return webCrawler.getStockFile("https://rubus.kz/node11/");
    }

    private List<RetailSystemStock> getListOfRetailSystemRubusFromFile() throws IOException {
        return reader.convertIntoObject(getFileFromWeb());
    }

}
