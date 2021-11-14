package com.pharm.demo.stock.data.repository;

import com.pharm.demo.stock.data.model.Customer;
import com.pharm.demo.stock.data.model.RetailSystemRubus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetailSystemRubusRepository extends MongoRepository<RetailSystemRubus, Long> {

}
