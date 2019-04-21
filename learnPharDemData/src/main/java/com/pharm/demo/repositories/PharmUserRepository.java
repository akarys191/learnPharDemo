package com.pharm.demo.repositories;

import com.pharm.demo.model.PharmUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Optional;

public interface PharmUserRepository extends CrudRepository<PharmUser, Long> {
    @Query("SELECT p FROM PharmUser p WHERE LOWER(p.userName) = LOWER(:userName)")
    public Optional<PharmUser> findByUserName(@Param("userName") String userName);
}
