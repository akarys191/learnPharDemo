package com.pharm.demo.services;

import com.pharm.demo.model.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineService extends CrudService<Medicine,Long>{
    Page<Medicine> findPaginated(Pageable pageable);

    List<Medicine> findByNameTerm(String term);
}
