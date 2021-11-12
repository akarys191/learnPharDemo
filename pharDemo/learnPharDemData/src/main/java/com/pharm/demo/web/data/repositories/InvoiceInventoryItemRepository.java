package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.dto.InventoryItemTotalSumsDTO;
import com.pharm.demo.web.data.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceInventoryItemRepository extends JpaRepository<InvoiceInventoryItem, Long> {
    @Query("SELECT inv FROM InvoiceInventoryItem inv WHERE inv.invoice.id =:invoiceId")
    Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, @Param("invoiceId") Long invoiceId);

    @Query("SELECT new com.pharm.demo.web.data.dto.InventoryItemTotalSumsDTO(sum(inv.paidSum),sum(inv.paidSum),sum(inv.paidSum)) FROM InvoiceInventoryItem inv WHERE inv.inventory.inventoryId =:inventoryId")
    InventoryItemTotalSumsDTO findTotalInventorySums(@Param("inventoryId") Long inventoryId);
}
