package com.pharm.demo.services.impl;

import com.pharm.demo.model.Sales;
import com.pharm.demo.repositories.SalesRepository;
import com.pharm.demo.services.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SalesJpaService implements SalesService {

    private final SalesRepository salesRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public SalesJpaService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Override
    public Sales findById(Long id) {
        return salesRepository.findById(id).orElse(null);
    }

    public Sales save(Sales supplier) {
        return salesRepository.save(supplier);
    }

    public Set<Sales> findAll() {
        LOGGER.info("ALL suppliers  in JPA found@@@@@@@@ ");
        Set<Sales> salesSet = new HashSet<>();
        salesRepository.findAll().forEach(salesSet::add);
        return salesSet;
    }

    public void deleteById(Long id) {
        salesRepository.deleteById(id);
    }

    public void delete(Sales object) {
        salesRepository.delete(object);
    }

    @Override
    public Page<Sales> findPaginated(Pageable pageable) {
        return salesRepository.findAll(pageable);
    }

    @Override
    public Page<Sales> findPaginateByCashRegistry(Pageable pageable, Long cashRegistryId) {
        return salesRepository.findByCashRegistryId(pageable, cashRegistryId);
    }
}
