package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.Customer;
import com.pharm.demo.services.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/rest/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping({"/all"})
    public Set<Customer> listCustomers() {
        return customerService.findAll();
    }
}
