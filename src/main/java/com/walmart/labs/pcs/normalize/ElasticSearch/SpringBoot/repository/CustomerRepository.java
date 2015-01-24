package com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.repository;

import java.util.List;
/**
 * Created by pzhong1 on 1/23/15.
 */

import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

    public Customer findByFirstName(String firstName);

    public List<Customer> findByLastName(String lastName);

    Page<Customer> findByFirstName(String name, PageRequest pageable);

}
