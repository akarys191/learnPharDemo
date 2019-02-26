package com.pharm.demo.repositories;

import com.pharm.demo.model.CategoryMed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMedRepository extends JpaRepository<CategoryMed, Long> {
}
