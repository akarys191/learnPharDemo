package com.pharm.demo.services.impl;

import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.repositories.PharmacistRepository;
import com.pharm.demo.services.PharmacistService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@Profile("impl")
public class PharmacistJpaService implements PharmacistService {

    private final PharmacistRepository pharmacistRepository;


    public PharmacistJpaService(PharmacistRepository pharmacistRepository) {
        this.pharmacistRepository = pharmacistRepository;
    }

    @Override
    public Pharmacist findById(Long id) {
        return pharmacistRepository.findById(id).orElse(null);
    }

    @Override
    public Pharmacist save(Pharmacist pharmacist) {
        return  pharmacistRepository.save(pharmacist);
    }

    @Override
    public Set<Pharmacist> findAll() {
        System.out.println("ALL pharmacists in JPA found@@@@@@@@ ");

        Set<Pharmacist> pharmacists = new HashSet<>();
         pharmacistRepository.findAll().forEach(pharmacists::add);
         return pharmacists;
    }

    @Override
    public void deleteById(Long id) {
        pharmacistRepository.deleteById(id);
    }

    @Override
    public void delete(Pharmacist object) {
        pharmacistRepository.delete(object);
    }


}
