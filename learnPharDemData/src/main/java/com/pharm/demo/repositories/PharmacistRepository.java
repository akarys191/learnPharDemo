package com.pharm.demo.repositories;

import com.pharm.demo.model.Pharmacist;
import org.springframework.data.repository.CrudRepository;

public interface PharmacistRepository extends PagingAndSortingRepository<Pharmacist,Long> {
}
