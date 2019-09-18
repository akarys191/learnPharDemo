package com.pharm.demo.services;

import com.pharm.demo.model.InvoiceInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceInventoryService extends CrudService<InvoiceInventory, Long> {
    Page<InvoiceInventory> findPaginated(Pageable pageable);

    Double getTotalPaidSum(Long invoiceId);
}
