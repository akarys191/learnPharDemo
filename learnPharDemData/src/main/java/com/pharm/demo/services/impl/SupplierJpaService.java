package com.pharm.demo.services.impl;

import com.pharm.demo.model.Supplier;
import com.pharm.demo.repositories.SupplierRepository;
import com.pharm.demo.services.SupplierService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SupplierJpaService implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierJpaService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return  supplierRepository.save(supplier);
    }

    @Override
    public Set<Supplier> findAll() {
        System.out.println("ALL suppliers  in JPA found@@@@@@@@ ");

        Set<Supplier> supplierSet = new HashSet<>();
         supplierRepository.findAll().forEach(supplierSet::add);
         return supplierSet;
    }

    @Override
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public void delete(Supplier object) {
        supplierRepository.delete(object);
    }
}
