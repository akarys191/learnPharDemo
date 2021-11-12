package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.CashExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashExpenseRepository extends JpaRepository<CashExpense, Long> {
}
