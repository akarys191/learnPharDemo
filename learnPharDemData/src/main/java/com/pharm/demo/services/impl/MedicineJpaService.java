package com.pharm.demo.services.impl;

import com.pharm.demo.model.Medicine;
import com.pharm.demo.repositories.MedicineRepository;
import com.pharm.demo.services.MedicineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class MedicineJpaService implements MedicineService {

    private final MedicineRepository medicineJpaRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public MedicineJpaService(MedicineRepository medicineJpaRepository) {
        this.medicineJpaRepository = medicineJpaRepository;
    }

    @Override
    public Medicine findById(Long aLong) {
        return this.medicineJpaRepository.findById(aLong).orElse(null);
    }

    @Override
    public List<Medicine> findByNameTerm(String term) {
        return this.medicineJpaRepository.searchTermFromName(term);
    }

    @Override
    public boolean exists(String barCode) {
        return Optional.ofNullable(this.medicineJpaRepository.findByBarcode(barCode)).isPresent();
    }


    @Override
    public Medicine findByBarcode(String term) {
        return this.medicineJpaRepository.findByBarcode(term);
    }

    @Override
    public Medicine save(Medicine medicine) {
        return medicineJpaRepository.save(medicine);
    }

    @Override
    public Set<Medicine> findAll() {
        LOGGER.info("ALL medicines  in JPA found@@@@@@@@ ");
        Set<Medicine> medicineSet = new HashSet<>();
        medicineJpaRepository.findAll().forEach(medicineSet::add);

        return medicineSet;
    }

    @Override
    public void delete(Medicine object) {
        medicineJpaRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        medicineJpaRepository.deleteById(aLong);
    }

    @Override
    public Page<Medicine> findPaginated(Pageable pageable) {
        return medicineJpaRepository.findAll(pageable);
    }

    @Override
    public Medicine saveFlush(Medicine object) {
        return medicineJpaRepository.saveAndFlush(object);
    }
}
