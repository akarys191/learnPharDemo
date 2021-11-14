package com.pharm.demo.stock.data.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class SaveToDatabaseTest {

    @Autowired
    private SaveToDatabase saveToDatabase;
    @Test
    public void testSaveRubusStock() {
        saveToDatabase.saveRubusStock();
    }

    @Test
    public void testGetFileFromWeb() {
    }

    @Test
    public void testGetListOfRetailSystemRubusFromFile() {
    }
}