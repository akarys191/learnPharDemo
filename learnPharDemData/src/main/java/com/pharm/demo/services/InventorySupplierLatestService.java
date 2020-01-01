package com.pharm.demo.services;

import com.pharm.demo.model.InventorySupplierPriceCost;
import com.pharm.demo.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventorySupplierLatestService extends CrudJpaService<InventorySupplierPriceCost, Long> {
    Page<InventorySupplierPriceCost> findPaginated(Pageable pageable);

    InventorySupplierPriceCost findLatestInventoryByInventoryAndSupplier(Long inventoryId, Long supplierId);
}
