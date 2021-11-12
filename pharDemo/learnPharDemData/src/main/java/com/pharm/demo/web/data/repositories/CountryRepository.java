package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
