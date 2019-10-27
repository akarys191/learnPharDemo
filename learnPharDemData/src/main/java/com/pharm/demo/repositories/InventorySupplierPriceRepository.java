package com.pharm.demo.repositories;

import com.pharm.demo.model.InventorySupplierLatestPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventorySupplierPriceRepository extends PagingAndSortingRepository<InventorySupplierLatestPrice, Long> {
    @Query("SELECT invSupPrice FROM InventorySupplierPriceRepository invSupPrice WHERE invSupPrice.inventory.inventoryId =:inventoryId and invSupPrice.supplier.id=:supplierId")
    InventorySupplierLatestPrice findInventorySupplierPriceByInventorySupplier(@Param("inventoryId") Long inventoryId, @Param("supplierId") Long supplierId);

}
