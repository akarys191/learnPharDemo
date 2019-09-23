package com.pharm.demo.repositories;

import com.pharm.demo.model.InvoiceInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceInventoryItemRepository extends PagingAndSortingRepository<InvoiceInventoryItem, Long> {
    @Query("SELECT inv FROM InvoiceInventoryItem inv WHERE inv.invoice.id =:invoiceId")
    Page<InvoiceInventoryItem> findInvoiceInventoryItemPaginated(Pageable pageable, @Param("invoiceId") Long invoiceId);
}
