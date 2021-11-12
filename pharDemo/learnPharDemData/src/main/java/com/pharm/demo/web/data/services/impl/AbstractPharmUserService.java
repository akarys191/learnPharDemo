package com.pharm.demo.web.data.services.impl;

import com.pharm.demo.web.data.model.PharmUser;
import com.pharm.demo.web.data.repositories.PharmUserBaseRepository;
import com.pharm.demo.web.data.services.PharmUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPharmUserService<T extends PharmUser> implements PharmUserService<T> {
    private final PharmUserBaseRepository abstractPharmUserRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public AbstractPharmUserService(PharmUserBaseRepository abstractPharmUserRepository) {
        this.abstractPharmUserRepository = abstractPharmUserRepository;
    }

    public T findById(Long id) {
        return (T) abstractPharmUserRepository.findById(id).orElse(null);
    }

    public T findByUserName(String userName) {
        return (T) abstractPharmUserRepository.findByUserName(userName).orElse(null);
    }

    @CacheEvict(value = "pharmUsers", allEntries = true)
    public PharmUser save(PharmUser pharmUser) {
        return (T) abstractPharmUserRepository.save(pharmUser);
    }

    @Cacheable(value = "pharmUsers")
    public Set<T> findAll() {
        LOGGER.info("ALL PharmUsers  in JPA found@@@@@@@@ ");
        Set<T> PharmUserSet = new HashSet<>();
        abstractPharmUserRepository.findAll().forEach(e -> PharmUserSet.add((T) e));
        return PharmUserSet;
    }

    @CacheEvict(value = "pharmUsers", allEntries = true)
    public void deleteById(Long id) {
        abstractPharmUserRepository.deleteById(id);
    }

    @CacheEvict(value = "pharmUsers", allEntries = true)
    public void delete(T object) {
        abstractPharmUserRepository.delete(object);
    }
}
