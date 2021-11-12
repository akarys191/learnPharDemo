package com.pharm.demo.web.controllers;

import com.pharm.demo.web.data.model.CashType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/general")
public class GeneralApiController {
    @RequestMapping({"/allCashTypes"})
    public Set<CashType> listCashTypes() {
        return Arrays.stream(CashType.values()).collect(Collectors.toSet());
    }
}
