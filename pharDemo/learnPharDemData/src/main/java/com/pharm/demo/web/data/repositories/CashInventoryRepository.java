package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.CashInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CashInventoryRepository extends JpaRepository<CashInventory, Long> {
    @Query("SELECT cashInv FROM CashInventory cashInv WHERE cashInv.inventoryVersionNumber =:inventoryVersionNumber")
    CashInventory findCashInventoryByInventoryVersionNumber(@Param("inventoryVersionNumber") Long inventoryVersionNumber);
}
