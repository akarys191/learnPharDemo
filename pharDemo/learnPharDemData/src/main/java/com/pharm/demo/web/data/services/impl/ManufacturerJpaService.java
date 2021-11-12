package com.pharm.demo.web.data.services.impl;

import com.pharm.demo.web.data.model.Manufacturer;
import com.pharm.demo.web.data.repositories.ManufacturerRepository;
import com.pharm.demo.web.data.services.ManufacturerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class ManufacturerJpaService implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerJpaService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer findById(Long aLong) {
        return this.manufacturerRepository.findById(aLong).orElse(null);
    }

    @Override
    public Manufacturer save(Manufacturer Manufacturer) {
        return manufacturerRepository.save(Manufacturer);
    }

    @Override
    public Set<Manufacturer> findAll() {
        System.out.println("ALL manufacturers of Med  in JPA found@@@@@@@@ ");
        Set<Manufacturer> manufacturerSet = new HashSet<>();
        manufacturerRepository.findAll().forEach(manufacturerSet::add);

        return manufacturerSet;
    }

    @Override
    public void delete(Manufacturer object) {
        manufacturerRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        manufacturerRepository.deleteById(aLong);
    }

    @Override
    public Manufacturer saveFlush(Manufacturer object) {
        return manufacturerRepository.saveAndFlush(object);
    }
}
