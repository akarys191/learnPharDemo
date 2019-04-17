package com.pharm.demo.web.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexMvcController {

    @RequestMapping({"","/","index","index.html"})
    public String index(){
       // MedicineServiceMap medicineServiceMap = new MedicineServiceMap();
        return "index";
    }

    @RequestMapping("/oups")
    public String oupsHandler(){
        return "notimplemented";
    }
}
