package com.pharm.demo.repositories;

import com.pharm.demo.model.CashExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashExpenseRepository extends JpaRepository<CashExpense, Long> {
}
