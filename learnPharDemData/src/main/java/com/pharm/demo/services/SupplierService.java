package com.pharm.demo.services;

import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService extends CrudJpaService<Supplier, Long> {
    Page<Supplier> findPaginated(Pageable pageable);
}
