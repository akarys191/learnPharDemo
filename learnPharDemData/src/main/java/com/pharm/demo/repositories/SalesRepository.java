package com.pharm.demo.repositories;

import com.pharm.demo.dto.CashRegistrySalesSumsDTO;
import com.pharm.demo.model.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    @Query("SELECT s FROM Sales s WHERE s.cashRegistry.cashRegistryId=:cashRegistryId")
    Page<Sales> findByCashRegistryId(Pageable pageable, Long cashRegistryId);

    @Query("SELECT new com.pharm.demo.dto.CashRegistrySalesSumsDTO(count(s), sum(s.soldSum)) FROM Sales s WHERE s.cashRegistry.cashRegistryId=:cashRegistryId")
    CashRegistrySalesSumsDTO getTotalSoldNumSumByCashRegistryId(Long cashRegistryId);
}
