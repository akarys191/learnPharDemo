package com.pharm.demo.repositories;

import com.pharm.demo.dto.InventoryItemSumsDTO;
import com.pharm.demo.model.InvoiceInventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceInventoryRepository extends PagingAndSortingRepository<InvoiceInventory, Long> {
    @Query("SELECT new com.pharm.demo.dto.InventoryItemSumsDTO(sum(inv.paidSum),sum(inv.priceSum)) FROM InvoiceInventory inInv left join inInv.invoiceInventoryItems inv WHERE inInv.id=:invoiceId")
    public InventoryItemSumsDTO getTotalPaidPriceSum(@Param("invoiceId") Long invoiceId);
}
