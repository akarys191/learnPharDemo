/*
import com.pharm.demo.LearnPharDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

*/
/*@RunWith(SpringRunner.class)
@WebAppConfiguration
@AutoConfigureCache*//*

 */
/*@SpringBootTest(classes = WebConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = WebConfig.class)*//*

 */
/*@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { IndexMvcController.class })
@ContextConfiguration(classes = WebConfig.class)
@AutoConfigureCache*//*

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnPharDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LearnPharDemoWebTest {


    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPage() throws Exception {
        System.out.println("testing.................");
        Object result = mockMvc.perform(get("/index"));
        this.mockMvc.perform(get("/index"))
                .andExpect(content().string(containsString("index")));
    }

}

*/
