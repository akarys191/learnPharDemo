package com.pharm.demo.repositories;

import com.pharm.demo.model.CashInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashInventoryRepository extends JpaRepository<CashInventory, Long> {
}
