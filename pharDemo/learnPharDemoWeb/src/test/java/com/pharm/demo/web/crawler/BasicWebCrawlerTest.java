package com.pharm.demo.web.crawler;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;
public class BasicWebCrawlerTest {

    private BasicWebCrawler basicWebCrawler;

    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
        basicWebCrawler = new BasicWebCrawler(restTemplate);
    }

    @Test
    public void getPageLinks() {
        File pageLinks = basicWebCrawler.getPageLinks("https://rubus.kz/node11/");
    }
}