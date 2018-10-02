package com.epam.spring.demo.services.map;

import com.epam.spring.demo.model.Medicine;
import com.epam.spring.demo.services.CrudService;
import com.epam.spring.demo.services.MedicineService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MedicineServiceMap extends  AbstractMapService<Medicine,Long> implements MedicineService {

    @Override
    public Medicine findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Medicine save(Medicine medicine) {
        return  super.save(medicine.getId(),medicine);
    }

    @Override
    public Set<Medicine> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Medicine object) {
        super.delete(object);
    }
}
