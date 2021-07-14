
package com.pharm.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@MockMvcTest
public class LearnPharDemoWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPage() throws Exception {
        System.out.println("testing.................");
//        this.mockMvc.perform(get("/oups"));
//                .andDo(print());
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("index")));
    }
}
