package com.pharm.demo.stock.data.controller;

import com.pharm.demo.stock.PharStockDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RetailSystemRubusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {
//        this.mockMvc.perform(get("/retails")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().);
    }

    @Test
    public void testFindById() {
    }

    @Test
    public void testUploadFile() {

    }
}