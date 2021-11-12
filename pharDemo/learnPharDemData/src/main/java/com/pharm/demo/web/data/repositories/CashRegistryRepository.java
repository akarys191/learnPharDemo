package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.CashRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CashRegistryRepository extends JpaRepository<CashRegistry, Long> {
    @Query("SELECT cr FROM CashRegistry cr WHERE cr.cashRegistryDate=:cashRegistryDate " +
            "ORDER BY cr.cashRegistryId desc")
    List<CashRegistry> findAllForDate(LocalDate cashRegistryDate);
}
