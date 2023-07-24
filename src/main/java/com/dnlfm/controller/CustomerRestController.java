package com.dnlfm.controller;

import com.dnlfm.domain.Customer;
import com.dnlfm.mapper.CustomerMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/customer")
@RestController
public class CustomerRestController {

    private final CustomerMapper customerMapper;

    public CustomerRestController(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        customerMapper.insertCustomer(customer);
        return customer;
    }

}
