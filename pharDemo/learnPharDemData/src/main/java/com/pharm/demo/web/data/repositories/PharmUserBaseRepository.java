package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.PharmUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@NoRepositoryBean
public interface PharmUserBaseRepository<T extends PharmUser, ID> extends CrudRepository<T, ID> {
    @Query("SELECT p FROM #{#entityName} p WHERE LOWER(p.userName) = LOWER(:userName)")
    public Optional<T> findByUserName(@Param("userName") String userName);
}
