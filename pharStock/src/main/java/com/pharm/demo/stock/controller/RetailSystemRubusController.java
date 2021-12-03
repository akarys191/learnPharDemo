package com.pharm.demo.stock.controller;

import com.pharm.demo.stock.exceptions.RetailSystemNotFound;
import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.service.RetailSystemStockService;
import com.pharm.demo.stock.service.RetailSystemStockFillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
public class RetailSystemRubusController {

    @Autowired
    private RetailSystemStockFillingService fillingService;
    @Autowired
    private RetailSystemStockService retailSystemRubusService;

    @GetMapping("/retails")
    public List<RetailSystemStock> findAll(){
        return retailSystemRubusService.listAll();
    }

    @GetMapping("/{id}")
    public RetailSystemStock findById(@PathVariable("id") BigInteger id){

        try {
            return retailSystemRubusService.findById(id);
        } catch (RetailSystemNotFound e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Retail system not Found", e);
        }
    }

    @PostMapping("/upload")
    public void uploadFile(){
        try {
            fillingService.saveRubusStock();
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error in filling part", e);
        }
    }

}
