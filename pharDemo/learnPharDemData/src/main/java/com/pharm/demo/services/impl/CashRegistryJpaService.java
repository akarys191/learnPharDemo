package com.pharm.demo.services.impl;

import com.pharm.demo.model.CashRegistry;
import com.pharm.demo.repositories.CashRegistryRepository;
import com.pharm.demo.services.CashRegistryService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CashRegistryJpaService implements CashRegistryService {

    private final CashRegistryRepository cashRegistryRepository;

    public CashRegistryJpaService(CashRegistryRepository cashRegistryRepository) {
        this.cashRegistryRepository = cashRegistryRepository;
    }

    @Override
    public CashRegistry findById(Long aLong) {
        return this.cashRegistryRepository.findById(aLong).orElse(null);
    }

    @Override
    public CashRegistry save(CashRegistry cashRegistry) {
        return cashRegistryRepository.save(cashRegistry);
    }

    @Override
    public CashRegistry saveFlush(CashRegistry object) {
        return cashRegistryRepository.saveAndFlush(object);
    }

    @Override
    public Set<CashRegistry> findAll() {
        System.out.println("ALL categories of Med  in JPA found@@@@@@@@ ");
        Set<CashRegistry> cashRegistrySet = new HashSet<>();
        cashRegistryRepository.findAll().forEach(cashRegistrySet::add);

        return cashRegistrySet;
    }

    @Override
    public void delete(CashRegistry object) {
        cashRegistryRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        cashRegistryRepository.deleteById(aLong);
    }

    @Override
    public CashRegistry findLatestForToday() {
        LocalDate todayDate = LocalDate.now();
        return Optional.ofNullable(cashRegistryRepository.findAllForDate(todayDate))
                .filter(cashRegistries -> !cashRegistries.isEmpty())
                .map(cashRegistries -> cashRegistries.get(0))
                .orElse(null);
    }
}
