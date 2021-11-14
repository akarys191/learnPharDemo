package com.pharm.demo.stock.data.service;

import com.pharm.demo.stock.data.model.RetailSystemRubus;
import com.pharm.demo.stock.data.repository.RetailSystemRubusRepository;
import com.pharm.demo.stock.data.service.crawler.BasicWebCrawler;
import com.pharm.demo.stock.data.service.reader.service.ReaderFromExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class SaveToDatabase {

    private final RetailSystemRubusRepository retailSystemRubusRepository;
    private final ReaderFromExcel reader;
    private final BasicWebCrawler webCrawler;

    public SaveToDatabase(RetailSystemRubusRepository retailSystemRubusRepository, ReaderFromExcel reader, BasicWebCrawler webCrawler) {
        this.retailSystemRubusRepository = retailSystemRubusRepository;
        this.reader = reader;
        this.webCrawler = webCrawler;
    }

    public void saveRubusStock(){
        try {
            List<RetailSystemRubus> listOfRetailSystemRubusFromFile = getListOfRetailSystemRubusFromFile();
            retailSystemRubusRepository.saveAll(listOfRetailSystemRubusFromFile);
        } catch (IOException e) {
            log.error("File not found",e);
        }
    }
    private File getFileFromWeb() throws IOException {

           return webCrawler.getStockFile("https://rubus.kz/node11/");
    }

    private List<RetailSystemRubus> getListOfRetailSystemRubusFromFile() throws IOException {
        return reader.convertIntoObject(getFileFromWeb());
    }

}
