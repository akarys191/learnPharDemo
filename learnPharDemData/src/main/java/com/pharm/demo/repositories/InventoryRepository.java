package com.pharm.demo.repositories;

import com.pharm.demo.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long> {
    @Query("SELECT inv FROM Inventory inv WHERE inv.inventoryId =:inventoryId")
    Page<Inventory> findInventoryPaginated(Pageable pageable, @Param("inventoryId") Long inventoryId);

    @Query("SELECT max(inv.inventoryId) FROM Inventory inv")
    Long findMaxInventoryId();

    @Query("SELECT inv FROM Inventory inv WHERE inv.inventoryId =:inventoryId and inv.medicine.id=:medicineId")
    Inventory findInventoryByMedicine(@Param("inventoryId") Long inventoryId, @Param("medicineId") Long medicineId);
}
