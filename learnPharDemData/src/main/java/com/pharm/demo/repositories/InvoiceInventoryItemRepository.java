package com.pharm.demo.repositories;

import com.pharm.demo.dto.InventoryItemSumsDTO;
import com.pharm.demo.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceInventoryItemRepository extends PagingAndSortingRepository<InvoiceInventoryItem, Long> {
    @Query("SELECT inv FROM InvoiceInventoryItem inv WHERE inv.invoice.id =:invoiceId")
    Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, @Param("invoiceId") Long invoiceId);

    @Query("SELECT new com.pharm.demo.dto.InventoryItemSumsDTO(sum(inv.paidSum),sum(inv.paidSum),sum(inv.paidSum)) FROM InvoiceInventoryItem inv WHERE inv.inventory.inventoryId =:inventoryId")
    InventoryItemSumsDTO findTotalInventorySums(@Param("inventoryId") Long inventoryId);
}
