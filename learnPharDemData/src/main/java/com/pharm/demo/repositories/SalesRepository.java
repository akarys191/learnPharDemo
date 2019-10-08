package com.pharm.demo.repositories;

import com.pharm.demo.model.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface SalesRepository extends PagingAndSortingRepository<Sales, Long> {
    @Query("SELECT s FROM Sales s WHERE s.cashRegistry.cashRegistryId=:cashRegistryId")
    Page<Sales> findByCashRegistryId(Pageable pageable, Long cashRegistryId);
}
