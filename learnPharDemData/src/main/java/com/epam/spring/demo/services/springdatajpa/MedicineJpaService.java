package com.epam.spring.demo.services.springdatajpa;

import com.epam.spring.demo.model.Medicine;
import com.epam.spring.demo.repositories.MedicineRepository;
import com.epam.spring.demo.services.MedicineService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class MedicineJpaService implements MedicineService {

    private final MedicineRepository medicineJpaRepository;

    public MedicineJpaService(MedicineRepository medicineJpaRepository) {
        this.medicineJpaRepository = medicineJpaRepository;
    }

    @Override
    public Medicine findById(Long aLong) {
        return this.medicineJpaRepository.findById(aLong).orElse(null);
    }

    @Override
    public Medicine save(Medicine medicine) {
        return medicineJpaRepository.save(medicine);
    }

    @Override
    public Set<Medicine> findAll() {
        System.out.println("ALL medicines  in JPA found@@@@@@@@ ");
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
}
