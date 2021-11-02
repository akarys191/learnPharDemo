package com.pharm.demo.services;

import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceInventoryItemService extends CrudJpaService<InvoiceInventoryItem, Long> {
    Page<InvoiceInventoryItem> findPaginated(Pageable pageable);

    Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, Long invoiceId);
}
