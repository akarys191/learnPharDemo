package com.epam.spring.spittrMvc.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping({"/", "/homepage"})
public class HomeController {

    @Value("${welcome.message}")
    private String message = "Hello World";

  //  @RequestMapping(value = "/",method=GET)
   @RequestMapping(method=GET)
    public String home(Map<String, Object> model) {
       /* System.out.println("Message::::::::::::::::::::::"+message);
        model.put("message", this.message);
       System.out.println(model.get("message"));*/

       return "home";
    }
}
