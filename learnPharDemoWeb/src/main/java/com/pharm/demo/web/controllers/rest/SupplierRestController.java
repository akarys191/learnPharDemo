package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.SupplierService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/rest/suppliers")
public class SupplierRestController {

    private final SupplierService supplierService;

    public SupplierRestController(SupplierService pharmacistservice) {
        this.supplierService = pharmacistservice;
    }

    @RequestMapping({"/all"})
    public Set<Supplier> listSuppliers() {
        return supplierService.findAll();
    }
}
