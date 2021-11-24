package com.pharm.demo.stock.data.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;


import java.util.Locale;

@Configuration
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class Config {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
