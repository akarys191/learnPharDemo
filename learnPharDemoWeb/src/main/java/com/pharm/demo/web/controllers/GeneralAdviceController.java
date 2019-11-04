package com.pharm.demo.web.controllers;

import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.MedicineService;
import com.pharm.demo.services.PharmacistService;
import com.pharm.demo.services.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.el.PropertyNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@ControllerAdvice
public class GeneralAdviceController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MedicineService medicineService;
    private final SupplierService supplierService;
    private final PharmacistService pharmacistService;

    public GeneralAdviceController(MedicineService medicineService,
                                   SupplierService supplierService, PharmacistService pharmacistService) {
        this.medicineService = medicineService;
        this.supplierService = supplierService;
        this.pharmacistService = pharmacistService;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised  ", ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("errMsg", ex);
        mav.addObject("stackTra", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("errorView");
        return mav;
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity handleErrorNotFound(HttpServletRequest req, Exception ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised  ", ex);
        return ResponseEntity.notFound().build();
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
