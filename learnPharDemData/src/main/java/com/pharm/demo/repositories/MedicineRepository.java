package com.pharm.demo.repositories;

import com.pharm.demo.model.Medicine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineRepository extends PagingAndSortingRepository<Medicine,Long> {
    @Query("SELECT m FROM Medicine m WHERE m.name LIKE %:term%")
    public List<Medicine> searchTermFromName(@Param("term") String term);
}
