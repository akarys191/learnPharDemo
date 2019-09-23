package com.pharm.demo.services.impl;

import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.repositories.InvoiceInventoryItemRepository;
import com.pharm.demo.services.InvoiceInventoryItemService;
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
public class InvoiceInventoryItemJpaService implements InvoiceInventoryItemService {

    private final InvoiceInventoryItemRepository inventoryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InvoiceInventoryItemJpaService(InvoiceInventoryItemRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Page<InvoiceInventoryItem> findPaginated(Pageable pageable) {
        return this.inventoryRepository.findAll(pageable);
    }

    @Override
    public Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, Long invoiceId) {
        return this.inventoryRepository.findInvoiceInventoryItemPaginated(pageable, invoiceId);
    }

    @Override
    public InvoiceInventoryItem findById(Long aLong) {
        return this.inventoryRepository.findById(aLong).orElse(null);
    }

    @Override
    public InvoiceInventoryItem save(InvoiceInventoryItem object) {
        return inventoryRepository.save(object);
    }

    @Override
    public Set<InvoiceInventoryItem> findAll() {
        LOGGER.info("ALL inventory  in JPA found@@@@@@@@ ");
        Set<InvoiceInventoryItem> inventorySet = new HashSet<>();
        inventoryRepository.findAll().forEach(inventorySet::add);
        return inventorySet;
    }

    @Override
    public void delete(InvoiceInventoryItem object) {
        inventoryRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        inventoryRepository.deleteById(aLong);
    }
}
