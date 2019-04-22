package com.pharm.demo.repositories;

import com.pharm.demo.model.Medicine;
import org.springframework.data.repository.CrudRepository;

public interface MedicineRepository extends PagingAndSortingRepository<Medicine,Long> {
}
