package com.pharm.demo.services;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService extends CrudService<Inventory, Long> {
    Page<Inventory> findPaginated(Pageable pageable);

    Page<InvoiceInventoryItem> findInvoiceInventoryItemsByInventoryIdPaginated(Pageable pageable, Long inventoryId);

    Long latestInventoryVersionNumber();

    Inventory findInventoryByVersionNumberAndMedicine(Long inventoryVersionNumber, Long medicineId);
}
