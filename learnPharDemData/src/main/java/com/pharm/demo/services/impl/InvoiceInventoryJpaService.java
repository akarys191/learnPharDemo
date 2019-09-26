package com.pharm.demo.services.impl;

import com.pharm.demo.dto.InventoryItemSumsDTO;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.repositories.InvoiceInventoryRepository;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class InvoiceInventoryJpaService implements InvoiceInventoryService {

    private final InvoiceInventoryRepository invoiceInventoryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public InvoiceInventoryJpaService(InvoiceInventoryRepository invoiceInventoryRepository) {
        this.invoiceInventoryRepository = invoiceInventoryRepository;
    }

    @Override
    public Page<InvoiceInventory> findPaginated(Pageable pageable) {
        return this.invoiceInventoryRepository.findAll(pageable);
    }

    @Override
    public InventoryItemSumsDTO getTotalPaidPriceSum(Long invoiceId) {
        return Optional.ofNullable(invoiceInventoryRepository.getTotalPaidPriceSum(invoiceId)).orElse(new InventoryItemSumsDTO());
    }

    @Override
    public InvoiceInventory findById(Long aLong) {
        return this.invoiceInventoryRepository.findById(aLong).orElse(null);
    }

    @Override
    public InvoiceInventory save(InvoiceInventory object) {
        return invoiceInventoryRepository.save(object);
    }

    @Override
    public Set<InvoiceInventory> findAll() {
        LOGGER.info("ALL invoices  in JPA found@@@@@@@@ ");
        Set<InvoiceInventory> invoiceInventorySet = new HashSet<>();
        invoiceInventoryRepository.findAll().forEach(invoiceInventorySet::add);
        return invoiceInventorySet;
    }

    @Override
    public void delete(InvoiceInventory object) {
        invoiceInventoryRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        invoiceInventoryRepository.deleteById(aLong);
    }
}
