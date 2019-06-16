package com.pharm.demo.services.impl;

import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.repositories.InvoiceInventoryRepository;
import com.pharm.demo.services.InvoiceInventoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class InvoiceInventoryJpaService implements InvoiceInventoryService {

    private final InvoiceInventoryRepository invoiceInventoryRepository;

    public InvoiceInventoryJpaService(InvoiceInventoryRepository invoiceInventoryRepository) {
        this.invoiceInventoryRepository = invoiceInventoryRepository;
    }

    @Override
    public Page<InvoiceInventory> findPaginated(Pageable pageable) {
        return this.invoiceInventoryRepository.findAll(pageable);
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
        System.out.println("ALL invoices  in JPA found@@@@@@@@ ");
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
