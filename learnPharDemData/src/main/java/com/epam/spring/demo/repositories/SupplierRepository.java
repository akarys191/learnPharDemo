package com.epam.spring.demo.repositories;

import com.epam.spring.demo.model.Medicine;
import com.epam.spring.demo.model.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier,Long> {
}
