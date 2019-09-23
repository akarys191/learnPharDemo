package com.pharm.demo.services;

import com.pharm.demo.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceInventoryItemService extends CrudService<InvoiceInventoryItem, Long> {
    Page<InvoiceInventoryItem> findPaginated(Pageable pageable);

    Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, Long invoiceId);
}
