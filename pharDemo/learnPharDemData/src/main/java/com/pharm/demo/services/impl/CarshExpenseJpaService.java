package com.pharm.demo.services.impl;

import com.pharm.demo.model.CashExpense;
import com.pharm.demo.repositories.CashExpenseRepository;
import com.pharm.demo.services.CashExpenseService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CarshExpenseJpaService implements CashExpenseService {

    private final CashExpenseRepository cashExpenseRepository;

    public CarshExpenseJpaService(CashExpenseRepository cashExpenseRepository) {
        this.cashExpenseRepository = cashExpenseRepository;
    }

    @Override
    public CashExpense findById(Long aLong) {
        return this.cashExpenseRepository.findById(aLong).orElse(null);
    }

    @Override
    public CashExpense save(CashExpense cashExpense) {
        return cashExpenseRepository.save(cashExpense);
    }

    @Override
    public CashExpense saveFlush(CashExpense object) {
        return cashExpenseRepository.saveAndFlush(object);
    }

    @Override
    public Set<CashExpense> findAll() {
        System.out.println("ALL categories of Med  in JPA found@@@@@@@@ ");
        Set<CashExpense> cashExpenseSet = new HashSet<>();
        cashExpenseRepository.findAll().forEach(cashExpenseSet::add);

        return cashExpenseSet;
    }

    @Override
    public void delete(CashExpense object) {
        cashExpenseRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        cashExpenseRepository.deleteById(aLong);
    }
}
