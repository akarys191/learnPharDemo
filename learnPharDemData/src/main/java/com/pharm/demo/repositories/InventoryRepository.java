package com.pharm.demo.repositories;

import com.pharm.demo.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends PagingAndSortingRepository<InvoiceInventoryItem, Long> {
    @Query("SELECT inv FROM InvoiceInventoryItem inv WHERE inv.invoice.id =:invoiceId")
    Page<InvoiceInventoryItem> findInvoiceInventoryPaginated(Pageable pageable, @Param("invoiceId") Long invoiceId);
}
