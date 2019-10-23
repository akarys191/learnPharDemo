package com.pharm.demo.services.impl;

import com.pharm.demo.model.Customer;
import com.pharm.demo.repositories.CustomerRepository;
import com.pharm.demo.services.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CustomerJpaService implements CustomerService {

    private final CustomerRepository CustomerRepository;

    public CustomerJpaService(CustomerRepository CustomerRepository) {
        this.CustomerRepository = CustomerRepository;
    }

    @Override
    public Customer findById(Long aLong) {
        return this.CustomerRepository.findById(aLong).orElse(null);
    }

    @Override
    public Customer save(Customer Customer) {
        return CustomerRepository.save(Customer);
    }

    @Override
    public Set<Customer> findAll() {
        System.out.println("ALL countries of Med  in JPA found@@@@@@@@ ");
        Set<Customer> CustomerSet = new HashSet<>();
        CustomerRepository.findAll().forEach(CustomerSet::add);

        return CustomerSet;
    }

    @Override
    public void delete(Customer object) {
        CustomerRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        CustomerRepository.deleteById(aLong);
    }
}
