package com.pharm.demo.services.impl;

import com.pharm.demo.model.CategoryMed;
import com.pharm.demo.repositories.CategoryMedRepository;
import com.pharm.demo.services.CategoryMedService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CategoryMedJpaService implements CategoryMedService {

    private final CategoryMedRepository categoryMedRepository;

    public CategoryMedJpaService(CategoryMedRepository categoryMedRepository) {
        this.categoryMedRepository = categoryMedRepository;
    }

    @Override
    public CategoryMed findById(Long aLong) {
        return this.categoryMedRepository.findById(aLong).orElse(null);
    }

    @Override
    public CategoryMed save(CategoryMed categoryMed) {
        return categoryMedRepository.save(categoryMed);
    }

    @Override
    public CategoryMed saveFlush(CategoryMed object) {
        return categoryMedRepository.saveAndFlush(object);
    }

    @Override
    public Set<CategoryMed> findAll() {
        System.out.println("ALL categories of Med  in JPA found@@@@@@@@ ");
        Set<CategoryMed> categoryMedSet = new HashSet<>();
        categoryMedRepository.findAll().forEach(categoryMedSet::add);

        return categoryMedSet;
    }

    @Override
    public void delete(CategoryMed object) {
        categoryMedRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        categoryMedRepository.deleteById(aLong);
    }
}
