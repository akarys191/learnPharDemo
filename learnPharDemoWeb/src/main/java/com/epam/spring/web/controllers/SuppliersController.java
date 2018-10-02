package com.epam.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuppliersController {

    @RequestMapping({"suppliers/"})
    public String listSuppliers(){
       // MedicineServiceMap medicineServiceMap = new MedicineServiceMap();
        return "suppliers/suppliers";
    }
}
