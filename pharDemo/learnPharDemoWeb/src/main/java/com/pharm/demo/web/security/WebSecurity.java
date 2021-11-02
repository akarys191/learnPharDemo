package com.pharm.demo.web.security;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("webSecurity")
public class WebSecurity {

        @PostConstruct
        public void postConstruct(){
            System.out.println("WebSecurity.......................>>>");
        }

        public boolean checkPathVar(UsernamePasswordAuthenticationToken authentication, int pathVar) {
              return true;
        }

        public boolean checkPathVar(UsernamePasswordAuthenticationToken authentication, String pathVar) {
            return true;
        }
}