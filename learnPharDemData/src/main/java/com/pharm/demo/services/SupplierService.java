package com.pharm.demo.services;

import com.pharm.demo.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService extends CrudService<Supplier,Long> {
    Page<Supplier> findPaginated(Pageable pageable);
}
