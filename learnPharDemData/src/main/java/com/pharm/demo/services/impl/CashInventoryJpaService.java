package com.pharm.demo.services.impl;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.repositories.CashInventoryRepository;
import com.pharm.demo.services.CashInventoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CashInventoryJpaService implements CashInventoryService {

    private final CashInventoryRepository cashInventoryRepository;

    public CashInventoryJpaService(CashInventoryRepository categoryMedRepository) {
        this.cashInventoryRepository = categoryMedRepository;
    }

    @Override
    public CashInventory findById(Long aLong) {
        return this.cashInventoryRepository.findById(aLong).orElse(null);
    }

    @Override
    public CashInventory save(CashInventory cashInventory) {
        return cashInventoryRepository.save(cashInventory);
    }

    @Override
    public Set<CashInventory> findAll() {
        System.out.println("ALL categories of Med  in JPA found@@@@@@@@ ");
        Set<CashInventory> cashInventorySet = new HashSet<>();
        cashInventoryRepository.findAll().forEach(cashInventorySet::add);

        return cashInventorySet;
    }

    @Override
    public void delete(CashInventory object) {
        cashInventoryRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        cashInventoryRepository.deleteById(aLong);
    }
}
