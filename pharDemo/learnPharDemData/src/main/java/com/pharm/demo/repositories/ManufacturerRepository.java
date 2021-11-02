package com.pharm.demo.repositories;

import com.pharm.demo.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
