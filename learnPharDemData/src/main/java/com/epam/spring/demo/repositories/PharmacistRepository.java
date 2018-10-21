package com.epam.spring.demo.repositories;

import com.epam.spring.demo.model.Pharmacist;
import com.epam.spring.demo.model.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface PharmacistRepository extends CrudRepository<Pharmacist,Long> {
}
