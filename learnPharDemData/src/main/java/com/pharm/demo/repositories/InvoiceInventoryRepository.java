package com.pharm.demo.repositories;

import com.pharm.demo.model.InvoiceInventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceInventoryRepository extends PagingAndSortingRepository<InvoiceInventory, Long> {
    @Query("SELECT sum(inv.paidSum) FROM InvoiceInventory inInv left join inInv.invoiceInventoryItems inv WHERE inInv.id=:invoiceId")
    public Double getTotalPaidSum(@Param("invoiceId") Long invoiceId);
}
