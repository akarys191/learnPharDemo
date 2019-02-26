
package com.pharm.demo;

import com.pharm.demo.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebConfig.class})
@ComponentScan
@EnableAutoConfiguration

public class  LearnPharDemoApplication extends SpringBootServletInitializer {

    private static Class applicationClass = LearnPharDemoApplication.class;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LearnPharDemoApplication.class, args);
    }



}

