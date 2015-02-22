package com.walmart.labs.pcs.normalize.aop;

import com.walmart.labs.pcs.normalize.aop.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by pzhong1 on 2/12/15.
 */

@SpringBootApplication
public class SampleAopApplication implements CommandLineRunner {
    @Autowired
    private PeopleService peopleService;

    @Override
    public void run(String... args) {
        System.out.println(this.peopleService.getName());
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleAopApplication.class, args);
    }
}
