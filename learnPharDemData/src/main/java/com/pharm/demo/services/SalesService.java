package com.pharm.demo.services;

import com.pharm.demo.dto.CashRegistrySalesSumsDTO;
import com.pharm.demo.model.Sales;
import com.pharm.demo.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SalesService extends CrudJpaService<Sales, Long> {
    Page<Sales> findPaginated(Pageable pageable);

    Page<Sales> findPaginateByCashRegistry(Pageable pageable, Long cashRegistryId);

    CashRegistrySalesSumsDTO getTotalSoldPriceNumSum(Long cashRegistryId);
}
