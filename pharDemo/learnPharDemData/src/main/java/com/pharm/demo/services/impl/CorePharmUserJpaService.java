package com.pharm.demo.services.impl;

import com.pharm.demo.model.PharmUser;
import com.pharm.demo.repositories.CorePharmUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Primary
@Service
@Profile("springdatajpa")
public class CorePharmUserJpaService extends AbstractPharmUserService<PharmUser> {
    public CorePharmUserJpaService(CorePharmUserRepository CorePharmUserRepository) {
        super(CorePharmUserRepository);
    }
}
