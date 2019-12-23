package com.pharm.demo.services;

import com.pharm.demo.model.Customer;
import com.pharm.demo.services.base.CrudJpaService;

public interface CustomerService extends CrudJpaService<Customer, Long> {
}
