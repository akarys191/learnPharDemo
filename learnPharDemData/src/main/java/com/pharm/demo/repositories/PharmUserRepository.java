package com.pharm.demo.repositories;

import com.pharm.demo.model.PharmUser;
import org.springframework.data.repository.CrudRepository;

public interface PharmUserRepository extends CrudRepository<PharmUser, Long> {
}
