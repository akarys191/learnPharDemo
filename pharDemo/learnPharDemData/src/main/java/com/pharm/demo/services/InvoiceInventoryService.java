package com.pharm.demo.services;

import com.pharm.demo.dto.InventoryItemSumsDTO;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceInventoryService extends CrudJpaService<InvoiceInventory, Long> {
    Page<InvoiceInventory> findPaginated(Pageable pageable);

    InventoryItemSumsDTO getTotalPaidPriceNumSum(Long invoiceId);
}
