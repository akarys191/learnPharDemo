package com.pharm.demo.repositories;

import com.pharm.demo.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long> {
    @Query("SELECT inv FROM Inventory inv WHERE inv.invoice.id =:invoiceId")
    Page<Inventory> findInvoiceInventoryPaginated(Pageable pageable, @Param("invoiceId") Long invoiceId);
}
