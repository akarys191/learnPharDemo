package com.pharm.demo.repositories;

import com.pharm.demo.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    @Query("SELECT m FROM Medicine m WHERE m.name LIKE %:term%")
    public List<Medicine> searchTermFromName(@Param("term") String term);

    @Query("SELECT m FROM Medicine m WHERE m.barCode=:barCode")
    public Medicine findByBarcode(@Param("barCode") String barCode);
}
