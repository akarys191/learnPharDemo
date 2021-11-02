package com.pharm.demo.services.impl;

import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.repositories.PharmacistRepository;
import com.pharm.demo.services.PharmacistService;
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
public class PharmacistJpaService extends AbstractPharmUserService<Pharmacist> implements PharmacistService {

    private final PharmacistRepository pharmacistRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public PharmacistJpaService(PharmacistRepository pharmacistRepository) {
        super(pharmacistRepository);
        this.pharmacistRepository = pharmacistRepository;
    }

    @Override
    public Pharmacist findById(Long id) {
        return pharmacistRepository.findById(id).orElse(null);
    }

    @Override
    @CacheEvict(value = "pharmacists", allEntries = true)
    public Pharmacist save(Pharmacist pharmacist) {
        return  pharmacistRepository.save(pharmacist);
    }

    @Override
    @Cacheable(value = "pharmacists")
    public Set<Pharmacist> findAll() {
        LOGGER.info("ALL pharmacists in JPA found@@@@@@@@ ");
        Set<Pharmacist> pharmacists = new HashSet<>();
         pharmacistRepository.findAll().forEach(pharmacists::add);
         return pharmacists;
    }

    @CacheEvict(value = "pharmacists", allEntries = true)
    public void removeCache() {
        LOGGER.info("pharmacists Cache were removed!");
    }

    @Override
    @CacheEvict(value = "pharmacists", allEntries = true)
    public void deleteById(Long id) {
        pharmacistRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "pharmacists", allEntries = true)
    public void delete(Pharmacist object) {
        pharmacistRepository.delete(object);
    }


}
