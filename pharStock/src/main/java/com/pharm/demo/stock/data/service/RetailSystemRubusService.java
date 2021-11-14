package com.pharm.demo.stock.data.service;

import com.pharm.demo.stock.data.model.RetailSystemRubus;
import com.pharm.demo.stock.data.repository.RetailSystemRubusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetailSystemRubusService {
    @Autowired
    private RetailSystemRubusRepository retailSystemRubusRepository;

    public List<RetailSystemRubus>  listAll(){
        return retailSystemRubusRepository.findAll();
    }
    public RetailSystemRubus findById(Long id){
       return retailSystemRubusRepository.findById(id).get();
    }

}
