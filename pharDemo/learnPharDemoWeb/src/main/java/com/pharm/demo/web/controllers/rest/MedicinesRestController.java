package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.web.data.model.Medicine;
import com.pharm.demo.web.data.services.MedicineService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/medicines")
public class MedicinesRestController {

    private final MedicineService medicineService;

    public MedicinesRestController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @RequestMapping({"/all"})
    public Set<Medicine> listAllMedicines() {
        return medicineService.findAll();
    }

    @RequestMapping({"/findByName"})
    public @ResponseBody
    List<Medicine> findMedicineByName(@Param("term") String term) {
        return medicineService.findByNameTerm(term);
    }


    @RequestMapping(value = "/findByBarcode", produces = " application/json")
    public @ResponseBody
    Medicine findByBarcode(@Param("term") String term) {
        return medicineService.findByBarcode(term);
    }
}
