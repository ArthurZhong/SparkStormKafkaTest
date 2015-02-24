package com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.service.imp;

import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.entities.Customer;
import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.repository.CustomerRepository;
import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.service.CustomerService;
import com.walmart.labs.pcs.normalize.utils.JsonUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.apache.log4j.Logger;

/**
 * Created by pzhong1 on 1/23/15.
 */
public class CustomerServiceImp implements CustomerService {

    private static final Logger logger = Logger.getLogger(CustomerServiceImp.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Client client;

    @Value("${spring.data.elasticsearch.index:customer}")
    private String customerIndex;

    @Value("${spring.data.elasticsearch.type:customerType}")
    private String customerType;

    @Value("${spring.data.elasticsearch.findOneTimeoutInMillis:200}")
    private long findOneTimeoutInMillis;

    @Value("${spring.data.elasticsearch.retryCnt:2}")
    private int retryCnt;

    @Value("${spring.data.elasticsearch.retryThrottleInMillis:100}")
    private long retryThrottleInMillis;

    @Override
    public Customer save(Customer post) {
        return customerRepository.save(post);
    }

    @Override
    public Customer findOne(String id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer findOneWithTimeThrottlingAndRetry(String id) {
        for (int retried = 0; retried <= retryCnt; retried++) {
            try {
                GetResponse response = client.prepareGet(customerIndex, customerType, id)
                        .execute().actionGet(findOneTimeoutInMillis);
                final String source = response.getSourceAsString();
                if (null == source) {
                    return null;
                }
                return JsonUtil.convertToObject(source, Customer.class);
            } catch (Exception logged) {
                if (retried == retryCnt) {
                    logger.warn("Failed to get extracted attributes for item " + id, logged);
                } else {
                    try {
                        Thread.sleep(retryThrottleInMillis);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return null;
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
