package com.pharm.demo.services.impl;

import com.pharm.demo.model.PharmUser;
import com.pharm.demo.repositories.PharmUserRepository;
import com.pharm.demo.services.PharmUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PharmUserJpaService implements PharmUserService {

    private final PharmUserRepository pharmUserRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public PharmUserJpaService(PharmUserRepository PharmUserRepository) {
        this.pharmUserRepository = PharmUserRepository;
    }

    @Override
    public PharmUser findById(Long id) {
        return pharmUserRepository.findById(id).orElse(null);
    }

    public PharmUser findByUserName(String userName) {
        return pharmUserRepository.findByUserName(userName).orElse(null);
    }

    @Override
    @CacheEvict(value = "pharmUsers", allEntries = true)
    public PharmUser save(PharmUser pharmUser) {
        return pharmUserRepository.save(pharmUser);
    }

    @Override
    @Cacheable(value = "pharmUsers")
    public Set<PharmUser> findAll() {
        LOGGER.info("ALL PharmUsers  in JPA found@@@@@@@@ ");
        Set<PharmUser> PharmUserSet = new HashSet<>();
        pharmUserRepository.findAll().forEach(PharmUserSet::add);
        return PharmUserSet;
    }

    @Override
    @CacheEvict(value = "pharmUsers", allEntries = true)
    public void deleteById(Long id) {
        pharmUserRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "pharmUsers", allEntries = true)
    public void delete(PharmUser object) {
        pharmUserRepository.delete(object);
    }
}
