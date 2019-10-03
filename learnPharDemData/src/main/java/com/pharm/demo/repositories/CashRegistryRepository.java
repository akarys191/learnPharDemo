package com.pharm.demo.repositories;

import com.pharm.demo.model.CashRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRegistryRepository extends JpaRepository<CashRegistry, Long> {
}
