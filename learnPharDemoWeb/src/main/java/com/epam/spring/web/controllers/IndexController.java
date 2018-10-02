package com.epam.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"","/","index","index.html"})
    public String index(){
       // MedicineServiceMap medicineServiceMap = new MedicineServiceMap();
        return "index";
    }
}
