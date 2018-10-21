package com.epam.spring.demo.repositories;

import com.epam.spring.demo.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MedicineRepository extends CrudRepository<Medicine,Long> {
}
