package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.Supplier;
import com.pharm.demo.web.data.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService extends CrudJpaService<Supplier, Long> {
    Page<Supplier> findPaginated(Pageable pageable);
}
