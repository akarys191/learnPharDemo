package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.dto.InventoryItemSumsDTO;
import com.pharm.demo.web.data.model.InvoiceInventory;
import com.pharm.demo.web.data.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceInventoryService extends CrudJpaService<InvoiceInventory, Long> {
    Page<InvoiceInventory> findPaginated(Pageable pageable);

    InventoryItemSumsDTO getTotalPaidPriceNumSum(Long invoiceId);
}
