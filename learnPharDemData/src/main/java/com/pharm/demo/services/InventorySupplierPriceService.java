package com.pharm.demo.services;

import com.pharm.demo.model.InventorySupplierLatestPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventorySupplierPriceService extends CrudService<InventorySupplierLatestPrice, Long> {
    Page<InventorySupplierLatestPrice> findPaginated(Pageable pageable);

    InventorySupplierLatestPrice findInventoryByInventoryAndSupplier(Long inventoryId, Long supplierId);
}
