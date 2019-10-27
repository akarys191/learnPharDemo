package com.pharm.demo.services.impl;

import com.pharm.demo.model.InventorySupplierLatestPrice;
import com.pharm.demo.repositories.InventorySupplierPriceRepository;
import com.pharm.demo.services.InventorySupplierPriceService;
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
public class InventorySupplierPriceJpaService implements InventorySupplierPriceService {

    private final InventorySupplierPriceRepository inventorySupplierPriceRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InventorySupplierPriceJpaService(InventorySupplierPriceRepository inventorySupplierPriceRepository) {
        this.inventorySupplierPriceRepository = inventorySupplierPriceRepository;
    }

    @Override
    public Page<InventorySupplierLatestPrice> findPaginated(Pageable pageable) {
        return this.inventorySupplierPriceRepository.findAll(pageable);
    }

    @Override
    public InventorySupplierLatestPrice findById(Long aLong) {
        return this.inventorySupplierPriceRepository.findById(aLong).orElse(null);
    }

    @Override
    public InventorySupplierLatestPrice findInventoryByInventoryAndSupplier(Long inventoryId, Long supplierId) {
        return this.inventorySupplierPriceRepository.findInventorySupplierPriceByInventorySupplier(inventoryId, supplierId);
    }

    @Override
    public InventorySupplierLatestPrice save(InventorySupplierLatestPrice object) {
        return inventorySupplierPriceRepository.save(object);
    }

    @Override
    public Set<InventorySupplierLatestPrice> findAll() {
        LOGGER.info("ALL inventory  in JPA found@@@@@@@@ ");
        Set<InventorySupplierLatestPrice> inventorySupplierPrices = new HashSet<>();
        inventorySupplierPriceRepository.findAll().forEach(inventorySupplierPrices::add);
        return inventorySupplierPrices;
    }

    @Override
    public void delete(InventorySupplierLatestPrice object) {
        inventorySupplierPriceRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        inventorySupplierPriceRepository.deleteById(aLong);
    }
}
