package com.pharm.demo.web.data.services.impl;

import com.pharm.demo.web.data.model.InvoiceInventoryItem;
import com.pharm.demo.web.data.repositories.InvoiceInventoryItemRepository;
import com.pharm.demo.web.data.services.InvoiceInventoryItemService;
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

    private final InvoiceInventoryItemRepository inventoryInvoiceRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InvoiceInventoryItemJpaService(InvoiceInventoryItemRepository inventoryRepository) {
        this.inventoryInvoiceRepository = inventoryRepository;
    }

    @Override
    public Page<InvoiceInventoryItem> findPaginated(Pageable pageable) {
        return this.inventoryInvoiceRepository.findAll(pageable);
    }

    @Override
    public Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, Long invoiceId) {
        return this.inventoryInvoiceRepository.findInvoiceInventoryItemPaginated(pageable, invoiceId);
    }

    @Override
    public InvoiceInventoryItem findById(Long aLong) {
        return this.inventoryInvoiceRepository.findById(aLong).orElse(null);
    }

    @Override
    public InvoiceInventoryItem save(InvoiceInventoryItem object) {
        return inventoryInvoiceRepository.save(object);
    }

    @Override
    public Set<InvoiceInventoryItem> findAll() {
        LOGGER.info("ALL inventory  in JPA found@@@@@@@@ ");
        Set<InvoiceInventoryItem> inventorySet = new HashSet<>();
        inventoryInvoiceRepository.findAll().forEach(inventorySet::add);
        return inventorySet;
    }

    @Override
    public void delete(InvoiceInventoryItem object) {
        inventoryInvoiceRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        inventoryInvoiceRepository.deleteById(aLong);
    }

    @Override
    public InvoiceInventoryItem saveFlush(InvoiceInventoryItem object) {
        return inventoryInvoiceRepository.saveAndFlush(object);
    }
}
