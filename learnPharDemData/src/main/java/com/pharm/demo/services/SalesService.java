package com.pharm.demo.services;

import com.pharm.demo.model.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SalesService extends CrudService<Sales, Long> {
    Page<Sales> findPaginated(Pageable pageable);

    Page<Sales> findPaginateByCashRegistry(Pageable pageable, Long cashRegistryId);
}
