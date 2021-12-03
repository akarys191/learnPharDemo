package com.pharm.demo.stock.data.controller;

import com.pharm.demo.stock.controller.RetailSystemRubusController;
import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.service.RetailSystemStockFillingService;
import com.pharm.demo.stock.service.RetailSystemStockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RetailSystemRubusController.class)
public class RetailSystemRubusControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RetailSystemStockFillingService retailSystemStockFillingService;
    @MockBean
    private RetailSystemStockService retailSystemStockService;


    @Test
    public void testFindAll() throws Exception {
        mvc.perform(get("/retails")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testFindById() throws Exception {
        BigInteger id = BigInteger.valueOf(123);

        RetailSystemStock retailSystemStock = new RetailSystemStock();
        retailSystemStock.set_id(id);
        retailSystemStock.setBarCode("123456789");

        when(retailSystemStockService.findById(id)).thenReturn(retailSystemStock);
        mvc.perform(get("/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._id", is(123)))
                .andExpect(jsonPath("$.barCode", is("123456789")));
    }

    @Test
    public void testUploadFile() throws Exception {
        mvc.perform(post("/upload")).andDo(print())
                .andExpect(status().isOk());
    }
}

