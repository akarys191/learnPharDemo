package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.InventorySupplierPriceCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventorySupplierPriceRepository extends JpaRepository<InventorySupplierPriceCost, Long> {
    @Query("SELECT invSupLatest FROM InventorySupplierPriceCost invSupLatest WHERE invSupLatest.inventory.inventoryId =:inventoryId and invSupLatest.supplier.id=:supplierId")
    List<InventorySupplierPriceCost> findInventorySupplierLatestByInventorySupplierId(@Param("inventoryId") Long inventoryId, @Param("supplierId") Long supplierId);

    @Query("SELECT invSupLatest FROM InventorySupplierPriceCost invSupLatest WHERE invSupLatest.inventory.inventoryId =:inventoryId and invSupLatest.supplier.id=:supplierId " +
            "ORDER BY invSupLatest.creationDateTime desc")
    Page<InventorySupplierPriceCost> findInventorySupplierPriceByInventorySupplierLatest(Pageable pageable, @Param("inventoryId") Long inventoryId, @Param("supplierId") Long supplierId);
}
