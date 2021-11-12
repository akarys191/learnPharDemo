package com.pharm.demo.web.data.services.impl;

import com.pharm.demo.web.data.model.PharmUser;
import com.pharm.demo.web.data.repositories.CorePharmUserRepository;
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
