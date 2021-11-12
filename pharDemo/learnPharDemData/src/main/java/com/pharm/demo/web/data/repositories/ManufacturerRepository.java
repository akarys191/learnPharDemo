package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
