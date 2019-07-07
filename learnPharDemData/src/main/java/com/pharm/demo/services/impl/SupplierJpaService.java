package com.pharm.demo.services.impl;

import com.pharm.demo.model.Supplier;
import com.pharm.demo.repositories.SupplierRepository;
import com.pharm.demo.services.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SupplierJpaService implements SupplierService {

    private final SupplierRepository supplierRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public SupplierJpaService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    @CacheEvict(value = "suppliers", allEntries = true)
    public Supplier save(Supplier supplier) {
        return  supplierRepository.save(supplier);
    }

    @Override
    @Cacheable(value = "suppliers")
    public Set<Supplier> findAll() {
        LOGGER.info("ALL suppliers  in JPA found@@@@@@@@ ");

        Set<Supplier> supplierSet = new HashSet<>();
        supplierRepository.findAll().forEach(supplierSet::add);
        return supplierSet;
    }

    @Override
    @CacheEvict(value = "suppliers", allEntries = true)
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "suppliers", allEntries = true)
    public void delete(Supplier object) {
        supplierRepository.delete(object);
    }

    @Override
    public Page<Supplier> findPaginated(Pageable pageable) {
           return supplierRepository.findAll(pageable);
    }
}
