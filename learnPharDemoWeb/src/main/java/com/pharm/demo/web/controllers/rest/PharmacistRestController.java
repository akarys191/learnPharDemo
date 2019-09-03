package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.services.PharmacistService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/rest/pharmacists")
public class PharmacistRestController {

    private final PharmacistService pharmacistservice;

    public PharmacistRestController(PharmacistService pharmacistservice) {
        this.pharmacistservice = pharmacistservice;
    }

    @RequestMapping({"/all"})
    public Set<Pharmacist> listPharmacists() {
        return pharmacistservice.findAll();
    }
}
