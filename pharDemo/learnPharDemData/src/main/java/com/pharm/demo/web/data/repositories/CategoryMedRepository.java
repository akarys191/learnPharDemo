package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.CategoryMed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMedRepository extends JpaRepository<CategoryMed, Long> {
}
