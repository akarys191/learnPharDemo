package com.epam.spring.web.controllers;

import com.epam.spring.demo.services.MedicineService;
import com.epam.spring.demo.services.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MedicinesController {
    private final MedicineService medicineService;

    public MedicinesController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @RequestMapping({"/medicines/"})
    public String listSuppliers(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "medicines/medicines";
    }
}
