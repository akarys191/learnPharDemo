package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.Inventory;
import com.pharm.demo.web.data.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT invItem FROM InvoiceInventoryItem invItem WHERE invItem.inventory.inventoryId =:inventoryId")
    Page<InvoiceInventoryItem> findInvoiceInventoryItemsPaginated(Pageable pageable, @Param("inventoryId") Long inventoryId);

    @Query("SELECT max(inv.inventoryVersionNumber) FROM Inventory inv")
    Long findMaxInventoryVersionNumber();

    @Query("SELECT inv FROM Inventory inv WHERE inv.inventoryVersionNumber =:inventoryVersionNumber and inv.medicine.id=:medicineId")
    Inventory findInventoryByMedicine(@Param("inventoryVersionNumber") Long inventoryVersionNumber, @Param("medicineId") Long medicineId);
}
