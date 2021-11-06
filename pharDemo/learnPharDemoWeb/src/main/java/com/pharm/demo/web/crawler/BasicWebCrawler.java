package com.pharm.demo.web.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;

public class BasicWebCrawler {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    public void getPageLinks(String url) {

        try {
            Connection.Response loginForm = Jsoup.connect(url).userAgent(USER_AGENT)
                    .followRedirects(false).execute();
            HashMap<String, String> cookies = new HashMap<>(loginForm.cookies());
            HashMap<String, String> formData = new HashMap<>();
            formData.put("password", "19690927");
            formData.put("username", "баккалиева");

            Jsoup.connect(url + "Users/auth").userAgent(USER_AGENT)
//                    .proxy("127.0.0.1", 8081)
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .data("username", "баккалиева")
                    .data("password", "19690927")
                    .followRedirects(true)
                    .execute();

            Connection.Response mainPage = Jsoup.connect("https://rubus.kz/node11").cookies(cookies).execute();
            System.out.println(mainPage.body());

            Connection.Response execute = Jsoup.connect(url + "Reports/rest_excel").cookies(cookies)
//                    .proxy("127.0.0.1", 8081)
                    .method(Connection.Method.POST).execute();
            System.out.println(execute.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BasicWebCrawler().getPageLinks("http://rubus.kz/node11/");
    }

}
