package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.PharmUser;
import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.services.PharmUserService;
import com.pharm.demo.services.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/rest/pharmacists")
public class PharmacistRestController {

    private final PharmacistService pharmacistservice;

    @Autowired
    PharmUserService userService;

    public PharmacistRestController(PharmacistService pharmacistservice) {
        this.pharmacistservice = pharmacistservice;
    }

    @RequestMapping({"/all"})
    public Set<Pharmacist> listPharmacists() {
        return pharmacistservice.findAll();
    }

    @RequestMapping({"/currentPharmUser"})
    public PharmUser getCurrentUser(Principal principal) {
        PharmUser user = userService.findByUserName(principal.getName());
        return user;
    }
}
