package com.pharm.demo.web.crawler.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BasicWebCrawlerClient {

    private final RestTemplate restTemplate;

    public BasicWebCrawlerClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }
}
