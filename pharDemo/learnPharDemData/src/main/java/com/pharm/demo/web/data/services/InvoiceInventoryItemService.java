package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.InvoiceInventoryItem;
import com.pharm.demo.web.data.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceInventoryItemService extends CrudJpaService<InvoiceInventoryItem, Long> {
    Page<InvoiceInventoryItem> findPaginated(Pageable pageable);

    Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, Long invoiceId);
}
