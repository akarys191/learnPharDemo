package com.pharm.demo.web.crawler.authorization;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(RubusAuthorization.class)
public class AuthorizationTest {


    private  MockRestServiceServer mockRestServiceServer;

    @Autowired
    private RubusAuthorization authorization;
    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }



    @Test
    public void authorize() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie","rubus=j5k0k6usqf0tdf0s5uhr6tnqi5; expires=Wed, 02-Nov-2022 12:12:28 GMT; Max-Age=30758400; path=/");
        DefaultResponseCreator headers1 = withSuccess().headers(headers);

        mockRestServiceServer
                .expect(requestTo("/Users/auth"))
                .andRespond(withSuccess().headers(headers).body("{\"status\":\"ok\",\"rules\":[],\"page\":\"\"}"));

        String cookie = authorization.authorize("");
        Assert.assertEquals(cookie,"rubus=j5k0k6usqf0tdf0s5uhr6tnqi5");
    }
}