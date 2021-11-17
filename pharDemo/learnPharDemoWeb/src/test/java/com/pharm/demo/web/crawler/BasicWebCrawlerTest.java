package com.pharm.demo.web.crawler;

import com.pharm.demo.web.crawler.authorization.RubusAuthorization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({BasicWebCrawler.class, RubusAuthorization.class})
public class BasicWebCrawlerTest {

    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private BasicWebCrawler basicWebCrawler;

    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void setUp()  {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }
    @Test
    public void test_getStockFile() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie","rubus=j5k0k6usqf0tdf0s5uhr6tnqi5; expires=Wed, 02-Nov-2022 12:12:28 GMT; Max-Age=30758400; path=/");

        mockRestServiceServer
                .expect(requestTo("/Users/auth"))
                .andRespond(withSuccess().headers(headers).body("{\"status\":\"ok\",\"rules\":[],\"page\":\"\"}"));

        mockRestServiceServer
                .expect(requestTo("/Reports/rest"))
                .andRespond(withSuccess().body("<input value=\"11.11.2021\" id=\"m_date_end\" type=\"text\" class=\"form-control r5_date\">" +
                        "" +"<input value=\"01.11.2021\" id=\"m_date_start\" type=\"text\" class=\"form-control r5_date\">"));
        mockRestServiceServer
                .expect(requestTo("/Reports/rest_excel"))
                .andRespond(withSuccess());
        mockRestServiceServer
                .expect(requestTo("/Reports/get_excel"))
                .andRespond(withSuccess().body("somthing"));

        File file = basicWebCrawler.getStockFile("");
        Assert.assertNotNull("File is null",file);
    }
}