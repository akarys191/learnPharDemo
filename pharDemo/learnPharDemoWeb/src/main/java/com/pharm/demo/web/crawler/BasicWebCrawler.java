package com.pharm.demo.web.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class BasicWebCrawler {

    private RestTemplate restTemplate;

    public BasicWebCrawler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    public File getPageLinks(String url) {

        try {
            Connection.Response loginForm = Jsoup.connect(url).userAgent(USER_AGENT)
                    .followRedirects(false).execute();
            HashMap<String, String> cookies = new HashMap<>(loginForm.cookies());
            HashMap<String, String> formData = new HashMap<>();
            formData.put("password", "19690927");
            formData.put("username", "баккалиева");
            //////todo authorization, отдельно тест, application property path
            //password environment variable authontificate authotificate(username);
            Jsoup.connect(url + "Users/auth").userAgent(USER_AGENT)
//                    .proxy("127.0.0.1", 8081)
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .data("username", "баккалиева")
                    .data("password", "19690927")
                    .execute();

            Connection.Response mainPage = Jsoup.connect("https://rubus.kz/node11").cookies(cookies).execute();
            System.out.println(mainPage.body());
            // отдельно prepare file
            HashMap<String, String>  reportsData = new HashMap<>();
//            str=&filter=0&sort=name_asc&filter_date=&filter_opt=0&filter_sell=0&filter_barcode=0
//            &like=0&group=0&parts=false&date_start=01.11.2021&date_end=07.11.2021
            reportsData.put("str", "");
            reportsData.put("filter", "0");
            reportsData.put("sort", "name_asc");
            reportsData.put("filter_date", "");
            reportsData.put("filter_opt", "0");
            reportsData.put("filter_sell", "0");
            reportsData.put("filter_barcode", "0");
            reportsData.put("like", "0");
            reportsData.put("group", "0");
            reportsData.put("parts", "false");
            reportsData.put("date_start", "01.11.2021");
            reportsData.put("date_end", "07.11.2021");
            //if response 200, get

            Connection.Response execute = Jsoup.connect(url + "Reports/rest_excel").cookies(cookies)
                    .data(reportsData)
//                    .proxy("127.0.0.1", 8081)
                    .method(Connection.Method.POST).execute();
            return downloadFile(url, cookies);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private File downloadFile(String url, HashMap<String, String> cookies) throws IOException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "rubus=" + cookies.get("rubus"));
        HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
        ResponseEntity<byte[]> exchange = restTemplate.exchange(url + "Reports/get_excel", HttpMethod.GET, requestEntity,
                byte[].class
        );
        File file = File.createTempFile("rubus", ".xls");
        StreamUtils.copy(exchange.getBody(), new FileOutputStream(file));
        return file;
    }

}
