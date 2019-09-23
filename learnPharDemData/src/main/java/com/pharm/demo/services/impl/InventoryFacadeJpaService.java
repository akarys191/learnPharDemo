package com.pharm.demo.services.impl;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.repositories.InventoryRepository;
import com.pharm.demo.services.InventoryFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
@Transactional
public class InventoryFacadeJpaService implements InventoryFacadeService {


    private final InventoryRepository inventoryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InventoryFacadeJpaService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Page<Inventory> findPaginated(Pageable pageable) {
        return this.inventoryRepository.findAll(pageable);
    }

    @Override
    public Page<Inventory> findInventoryPaginated(Pageable pageable, Long invoiceId) {
        return this.inventoryRepository.findInventoryPaginated(pageable, invoiceId);
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
        LOGGER.info("ALL inventory  in JPA found@@@@@@@@ ");
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
