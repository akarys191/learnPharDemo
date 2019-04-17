package com.pharm.demo.repositories;

import com.pharm.demo.model.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier,Long> {
}
