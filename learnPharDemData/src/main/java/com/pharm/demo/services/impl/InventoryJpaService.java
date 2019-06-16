package com.pharm.demo.services.impl;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.repositories.InventoryRepository;
import com.pharm.demo.services.InventoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class InventoryJpaService implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryJpaService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Page<Inventory> findPaginated(Pageable pageable) {
        return this.inventoryRepository.findAll(pageable);
    }

    @Override
    public Inventory findById(Long aLong) {
        return this.inventoryRepository.findById(aLong).orElse(null);
    }

    @Override
    public Inventory save(Inventory object) {
        return inventoryRepository.save(object);
    }

    @Override
    public Set<Inventory> findAll() {
        System.out.println("ALL inventory  in JPA found@@@@@@@@ ");
        Set<Inventory> inventorySet = new HashSet<>();
        inventoryRepository.findAll().forEach(inventorySet::add);
        return inventorySet;
    }

    @Override
    public void delete(Inventory object) {
        inventoryRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        inventoryRepository.deleteById(aLong);
    }
}
