package com.pharm.demo.services;

import com.pharm.demo.model.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineService extends CrudService<Medicine,Long>{
    Page<Medicine> findPaginated(Pageable pageable);

}
