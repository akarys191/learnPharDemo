package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.MedicineService;
import com.pharm.demo.services.PharmacistService;
import com.pharm.demo.services.SupplierService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

@ControllerAdvice
public class GeneralAdviceController {

    private final MedicineService medicineService;
    private final SupplierService supplierService;
    private final PharmacistService pharmacistService;

    public GeneralAdviceController(MedicineService medicineService,
                                   SupplierService supplierService, PharmacistService pharmacistService) {
        this.medicineService = medicineService;
        this.supplierService = supplierService;
        this.pharmacistService = pharmacistService;
    }

    @ModelAttribute("suppliers")
    public Set<Supplier> getSuppliers() {
        Set<Supplier> supplierSet = supplierService.findAll();
        return supplierSet;
    }

    @ModelAttribute("acceptingPharmacists")
    public Set<Pharmacist> getPharmacists() {
        Set<Pharmacist> pharmacistSet = pharmacistService.findAll();
        return pharmacistSet;
    }
}
