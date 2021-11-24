package com.pharm.demo.stock.data.service;

import com.pharm.demo.stock.data.exceptions.RetailSystemNotFound;
import com.pharm.demo.stock.data.model.RetailSystemStock;
import com.pharm.demo.stock.data.repository.RetailSystemStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class RetailSystemStockService {
    @Autowired
    private RetailSystemStockRepository retailSystemRubusRepository;

    public List<RetailSystemStock>  listAll(){
        return retailSystemRubusRepository.findAll();
    }
    public RetailSystemStock findById(BigInteger id) throws RetailSystemNotFound {
        RetailSystemStock by_id = retailSystemRubusRepository.findBy_id(id);
        if (by_id!=null){
            return by_id;
        }else throw new RetailSystemNotFound("Retail not found by this id",id);
    }
}
