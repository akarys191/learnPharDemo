package com.epam.spring.demo.services.map;

import com.epam.spring.demo.model.Pharmacist;
import com.epam.spring.demo.services.CrudService;
import com.epam.spring.demo.services.PharmacistService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Set;


@Service
public class PharmacistServiceMap extends  AbstractMapService<Pharmacist,Long> implements PharmacistService {

    @Override
    public Pharmacist findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Pharmacist save(Pharmacist pharmacist) {
        return  super.save(pharmacist);
    }

    @Override
    public Set<Pharmacist> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pharmacist object) {
        super.delete(object);
    }


}
