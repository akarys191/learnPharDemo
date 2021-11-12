package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.Medicine;
import com.pharm.demo.web.data.services.base.CrudJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineService extends CrudJpaService<Medicine, Long> {
    boolean exists(String barCode);

    Page<Medicine> findPaginated(Pageable pageable);

    List<Medicine> findByNameTerm(String term);

    Medicine findByBarcode(String barCode);
}
