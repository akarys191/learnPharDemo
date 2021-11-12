package com.pharm.demo.web.data.services.impl;

import com.pharm.demo.web.data.model.InventorySupplierPriceCost;
import com.pharm.demo.web.data.repositories.InventorySupplierPriceRepository;
import com.pharm.demo.web.data.services.InventorySupplierLatestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
@Transactional
public class InventorySupplierPriceJpaService implements InventorySupplierLatestService {

    private final InventorySupplierPriceRepository inventorySupplierPriceRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InventorySupplierPriceJpaService(InventorySupplierPriceRepository inventorySupplierPriceRepository) {
        this.inventorySupplierPriceRepository = inventorySupplierPriceRepository;
    }

    @Override
    public Page<InventorySupplierPriceCost> findPaginated(Pageable pageable) {
        return this.inventorySupplierPriceRepository.findAll(pageable);
    }

    @Override
    public InventorySupplierPriceCost findById(Long aLong) {
        return this.inventorySupplierPriceRepository.findById(aLong).orElse(null);
    }

    @Override
    public InventorySupplierPriceCost findLatestInventoryByInventoryAndSupplier(Long inventoryId, Long supplierId) {
        Page<InventorySupplierPriceCost> pagedSuppliers = this.inventorySupplierPriceRepository.findInventorySupplierPriceByInventorySupplierLatest(new PageRequest(0, 1), inventoryId, supplierId);
        if (!pagedSuppliers.getContent().isEmpty()) {
            return pagedSuppliers.getContent().get(0);
        }
        return null;
    }

    @Override
    public InventorySupplierPriceCost save(InventorySupplierPriceCost object) {
        return inventorySupplierPriceRepository.save(object);
    }

    @Override
    public Set<InventorySupplierPriceCost> findAll() {
        LOGGER.info("ALL inventory  in JPA found@@@@@@@@ ");
        Set<InventorySupplierPriceCost> inventorySupplierPrices = new HashSet<>();
        inventorySupplierPriceRepository.findAll().forEach(inventorySupplierPrices::add);
        return inventorySupplierPrices;
    }

    @Override
    public void delete(InventorySupplierPriceCost object) {
        inventorySupplierPriceRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        inventorySupplierPriceRepository.deleteById(aLong);
    }

    @Override
    public InventorySupplierPriceCost saveFlush(InventorySupplierPriceCost object) {
        return inventorySupplierPriceRepository.saveAndFlush(object);
    }
}
