package com.epam.spring.web.controllers;

import com.epam.spring.demo.services.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuppliersController {

    private final SupplierService supplierService;

    public SuppliersController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }
    @RequestMapping({"suppliers/"})
    public String listSuppliers(Model model){
        model.addAttribute("suppliers", supplierService.findAll() );
        return "suppliers/suppliers";
    }
}
