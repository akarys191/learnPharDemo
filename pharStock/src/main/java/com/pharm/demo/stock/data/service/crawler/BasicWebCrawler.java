package com.pharm.demo.stock.data.service.crawler;


import com.pharm.demo.stock.data.service.crawler.authorization.RubusAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class BasicWebCrawler {


    private RestTemplate restTemplate;
    private RubusAuthorization authorization;

    public BasicWebCrawler(RestTemplate restTemplate, RubusAuthorization authorization) {
        this.restTemplate = restTemplate;
        this.authorization = authorization;
    }

    public File getStockFile(String url) throws IOException {
        try {
            String authorizeCookie = authorization.authorize(url);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Cookie", authorizeCookie);
            ResponseEntity<String> stringResponseEntity = prepareFile(url, requestHeaders);
            if (stringResponseEntity.getStatusCode()==HttpStatus.OK) {
                return downloadFile(url, requestHeaders);
            } else {
                throw new IllegalStateException("failed to prepare rubus stock excel");
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw e;
        }

    }

    private  ResponseEntity<String> prepareFile(String url, HttpHeaders requestHeaders)  {


        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(url+"Reports/rest", HttpMethod.GET, requestEntity, String.class);
        String exchangeBody = Objects.requireNonNull(exchange.getBody());
        String dateStart = exchangeBody.substring(exchangeBody.indexOf("m_date_end") - 16, exchangeBody.indexOf("m_date_end") - 5);
        String dateEnd = exchangeBody.substring(exchangeBody.indexOf("m_date_start") - 16, exchangeBody.indexOf("m_date_start") - 5);

        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("str", "");
        map.add("filter", "0");
        map.add("sort", "name_asc");
        map.add("filter_date", "");
        map.add("filter_opt", "0");
        map.add("filter_sell", "0");
        map.add("filter_barcode", "0");
        map.add("like", "0");
        map.add("group", "0");
        map.add("parts", "false");
        map.add("date_start", dateStart);
        map.add("date_end", dateEnd);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, requestHeaders);
        return restTemplate.postForEntity(url + "Reports/rest_excel", request, String.class);
    }

    private File downloadFile( String url, HttpHeaders requestHeaders) throws IOException {

        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<byte[]> exchange = restTemplate.exchange(url + "Reports/get_excel", HttpMethod.GET, requestEntity,
                byte[].class
        );
        File file = File.createTempFile("rubus", ".xls");
        StreamUtils.copy(Objects.requireNonNull(exchange.getBody()), new FileOutputStream(file));
        return file;
    }

}
