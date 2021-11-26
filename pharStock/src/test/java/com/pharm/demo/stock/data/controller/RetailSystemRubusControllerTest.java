package com.pharm.demo.stock.data.controller;

import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.data.repository.RetailSystemStockRepository;
import com.pharm.demo.stock.data.service.RetailSystemStockFillingService;
import com.pharm.demo.stock.data.service.RetailSystemStockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RetailSystemRubusControllerTest {

    @Autowired
    private MockMvc mvc;


    @InjectMocks
    private RetailSystemStockService retailSystemRubusService;

    @MockBean
    private List<RetailSystemStock> list;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFindAll() throws Exception {
        mvc.perform(get("/retails")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testFindById() {
    }

    @Test
    public void testUploadFile() {

    }
}

