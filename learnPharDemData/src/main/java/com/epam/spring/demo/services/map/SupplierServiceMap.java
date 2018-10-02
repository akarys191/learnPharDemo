package com.epam.spring.demo.services.map;

import com.epam.spring.demo.model.Pharmacist;
import com.epam.spring.demo.model.Supplier;
import com.epam.spring.demo.services.CrudService;
import com.epam.spring.demo.services.SupplierService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SupplierServiceMap extends  AbstractMapService<Supplier,Long> implements SupplierService {

    @Override
    public Supplier findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return  super.save(supplier.getId(),supplier);
    }

    @Override
    public Set<Supplier> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Supplier object) {
        super.delete(object);
    }
}
