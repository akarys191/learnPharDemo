package com.pharm.demo.stock.service.crawler.authorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Component
public class RubusAuthorization {

    @Value("${userName}")
    private String username;
    @Value("${password}")
    private String password;

    private RestTemplate restTemplate;

    public RubusAuthorization(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String authorize(String url) {
        username = "баккалиева";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url + "Users/auth", request, String.class);

        if (stringResponseEntity.getBody().equals("{\"status\":\"ok\",\"rules\":[],\"page\":\"\"}")){
        String cookie = Objects.requireNonNull(stringResponseEntity.getHeaders().get("Set-Cookie")).get(0);
        return cookie.substring(cookie.indexOf("rubus="), cookie.indexOf(";"));
        } else return null;

    }
}
