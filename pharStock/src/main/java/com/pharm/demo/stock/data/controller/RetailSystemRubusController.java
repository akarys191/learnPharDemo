package com.pharm.demo.stock.data.controller;

import com.pharm.demo.stock.data.model.RetailSystemRubus;
import com.pharm.demo.stock.data.service.RetailSystemRubusService;
import com.pharm.demo.stock.data.service.SaveToDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RetailSystemRubusController {

    @Autowired
    private SaveToDatabase saveToDatabase;
    @Autowired
    private RetailSystemRubusService retailSystemRubusService;

    @GetMapping("/retails")
    public List<RetailSystemRubus> findAll(){
        return retailSystemRubusService.listAll();
    }

    @GetMapping("/upload")
    public void uploadFile(){
        saveToDatabase.saveRubusStock();
    }

}
