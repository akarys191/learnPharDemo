package com.pharm.demo.web.data.repositories;

import com.pharm.demo.web.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
