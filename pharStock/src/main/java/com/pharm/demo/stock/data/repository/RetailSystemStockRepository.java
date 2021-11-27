package com.pharm.demo.stock.data.repository;

import com.pharm.demo.stock.data.model.RetailSystemStock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface RetailSystemStockRepository extends MongoRepository<RetailSystemStock, Long> {
    RetailSystemStock findBy_id(BigInteger e);
}
