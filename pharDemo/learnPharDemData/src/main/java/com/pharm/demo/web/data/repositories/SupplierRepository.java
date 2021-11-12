package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
