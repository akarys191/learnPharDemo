package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.InventorySupplierPriceCost;
import com.pharm.demo.web.data.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventorySupplierLatestService extends CrudJpaService<InventorySupplierPriceCost, Long> {
    Page<InventorySupplierPriceCost> findPaginated(Pageable pageable);

    InventorySupplierPriceCost findLatestInventoryByInventoryAndSupplier(Long inventoryId, Long supplierId);
}
