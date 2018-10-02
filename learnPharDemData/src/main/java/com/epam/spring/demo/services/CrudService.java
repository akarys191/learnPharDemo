package com.epam.spring.demo.services;

import com.epam.spring.demo.model.Medicine;

import java.util.Set;

public interface CrudService<T,ID> {
    T findById(ID id);
    T save (T supplier);
    Set<T> findAll();
    void delete(T object);
    void deleteById(ID id);
}
