package com.pharm.demo.services.impl;

import com.pharm.demo.model.PharmUser;
import com.pharm.demo.repositories.PharmUserRepository;
import com.pharm.demo.services.PharmUserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PharmUserJpaService implements PharmUserService {

    private final PharmUserRepository pharmUserRepository;

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
    public PharmUser save(PharmUser pharmUser) {
        return pharmUserRepository.save(pharmUser);
    }

    @Override
    public Set<PharmUser> findAll() {
        System.out.println("ALL PharmUsers  in JPA found@@@@@@@@ ");
        Set<PharmUser> PharmUserSet = new HashSet<>();
        pharmUserRepository.findAll().forEach(PharmUserSet::add);
        return PharmUserSet;
    }

    @Override
    public void deleteById(Long id) {
        pharmUserRepository.deleteById(id);
    }

    @Override
    public void delete(PharmUser object) {
        pharmUserRepository.delete(object);
    }
}
