package com.pharm.demo.services;

import com.pharm.demo.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService extends CrudService<Inventory, Long> {
    Page<Inventory> findPaginated(Pageable pageable);
    Page<Inventory> findInventoryPaginated(Pageable pageable, Long inventoryId);
    Long latestInventoryId();
    Inventory findInventoryByMedicine(Long inventoryId, Long medicineId);
}
