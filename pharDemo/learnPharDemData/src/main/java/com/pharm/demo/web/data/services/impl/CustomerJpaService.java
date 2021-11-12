package com.pharm.demo.web.data.services.impl;

import com.pharm.demo.web.data.model.Customer;
import com.pharm.demo.web.data.repositories.CustomerRepository;
import com.pharm.demo.web.data.services.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class CustomerJpaService implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerJpaService(CustomerRepository CustomerRepository) {
        this.customerRepository = CustomerRepository;
    }

    @Override
    public Customer findById(Long aLong) {
        return this.customerRepository.findById(aLong).orElse(null);
    }

    @Override
    public Customer save(Customer Customer) {
        return customerRepository.save(Customer);
    }

    @Override
    public Set<Customer> findAll() {
        System.out.println("ALL countries of Med  in JPA found@@@@@@@@ ");
        Set<Customer> CustomerSet = new HashSet<>();
        customerRepository.findAll().forEach(CustomerSet::add);

        return CustomerSet;
    }

    @Override
    public void delete(Customer object) {
        customerRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        customerRepository.deleteById(aLong);
    }

    @Override
    public Customer saveFlush(Customer object) {
        return this.customerRepository.saveAndFlush(object);
    }
}
