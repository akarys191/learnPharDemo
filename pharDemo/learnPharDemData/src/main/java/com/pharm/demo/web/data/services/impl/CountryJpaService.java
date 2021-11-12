package com.pharm.demo.web.data.services.impl;

import com.pharm.demo.web.data.model.Country;
import com.pharm.demo.web.data.repositories.CountryRepository;
import com.pharm.demo.web.data.services.CountryService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CountryJpaService implements CountryService {

    private final CountryRepository countryRepository;

    public CountryJpaService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country findById(Long aLong) {
        return this.countryRepository.findById(aLong).orElse(null);
    }

    @Override
    public Country save(Country Country) {
        return countryRepository.save(Country);
    }

    @Override
    public Set<Country> findAll() {
        System.out.println("ALL countries of Med  in JPA found@@@@@@@@ ");
        Set<Country> countrySet = new HashSet<>();
        countryRepository.findAll().forEach(countrySet::add);

        return countrySet;
    }

    @Override
    public void delete(Country object) {
        countryRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        countryRepository.deleteById(aLong);
    }

    @Override
    public Country saveFlush(Country object) {
        return countryRepository.saveAndFlush(object);
    }
}
