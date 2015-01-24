package com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.service.imp;

import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.entities.Customer;
import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.repository.CustomerRepository;
import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by pzhong1 on 1/23/15.
 */
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer post) {
        return customerRepository.save(post);
    }

    @Override
    public Customer findOne(String id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> findByCustomerName(String customerName, PageRequest pageRequest) {
        return customerRepository.findByFirstName(customerName, pageRequest);
    }
}
