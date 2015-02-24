package com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.service;

import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by pzhong1 on 1/23/15.
 */
public interface CustomerService{
    Customer save(Customer post);
    Customer findOne(String id);
    Customer findOneWithTimeThrottlingAndRetry(String id);
    Iterable<Customer> findAll();
    public Page<Customer> findByCustomerName(String customerName, PageRequest pageRequest);
}
