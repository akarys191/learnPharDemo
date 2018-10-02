package com.epam.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MedicinesController {

    @RequestMapping({"/medicines/"})
    public String listSuppliers(){
       // MedicineServiceMap medicineServiceMap = new MedicineServiceMap();
        return "medicines/medicines";
    }
}
