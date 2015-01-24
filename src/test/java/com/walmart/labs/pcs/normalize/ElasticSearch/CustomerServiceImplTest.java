//package com.walmart.labs.pcs.normalize.ElasticSearch;
//
//import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.application.SampleElasticsearchApplication;
//import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.entities.Customer;
//import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.service.CustomerService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
///**
//* Created by pzhong1 on 1/23/15.
//*/
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SampleElasticsearchApplication.class)
//public class CustomerServiceImplTest {
//    @Autowired
//    private CustomerService customerService;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Before
//    public void before() {
//        elasticsearchTemplate.deleteIndex(Customer.class);
//        elasticsearchTemplate.createIndex(Customer.class);
//        elasticsearchTemplate.putMapping(Customer.class);
//        elasticsearchTemplate.refresh(Customer.class, true);
//    }
//
//    //@Test
//    public void testSave() throws Exception {
//        Customer customer = new Customer("hello", "world");
//        customerService.save(customer);
//        assertThat(customer.getId(), notNullValue());
//    }
//
//    @Test
//    public void test() throws Exception {
//        String str = "";
//        assertThat(str, notNullValue());
//    }
//
//    //@Test
//    public void testFindByCustomerName() throws Exception {
//        Page<Customer> customers  = customerService.findByCustomerName("hello", new PageRequest(0, 10));
//        assertThat(customers.getTotalElements(), is(1L));
//    }
//}
