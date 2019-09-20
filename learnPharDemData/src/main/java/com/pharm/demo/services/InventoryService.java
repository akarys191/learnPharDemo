package com.pharm.demo.services;

import com.pharm.demo.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService extends CrudService<InvoiceInventoryItem, Long> {
    Page<InvoiceInventoryItem> findPaginated(Pageable pageable);

    Page<InvoiceInventoryItem> findInvoiceInventoryPaginated(Pageable pageable, Long invoiceId);
}
