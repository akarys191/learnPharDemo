package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.Inventory;
import com.pharm.demo.web.data.model.InvoiceInventoryItem;
import com.pharm.demo.web.data.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService extends CrudJpaService<Inventory, Long> {
    Page<Inventory> findPaginated(Pageable pageable);

    Page<InvoiceInventoryItem> findInvoiceInventoryItemsByInventoryIdPaginated(Pageable pageable, Long inventoryId);

    Long latestInventoryVersionNumber();

    Inventory findInventoryByVersionNumberAndMedicine(Long inventoryVersionNumber, Long medicineId);
}
