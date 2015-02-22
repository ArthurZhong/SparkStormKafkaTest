package com.walmart.labs.pcs.normalize.aop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by pzhong1 on 2/12/15.
 */

@Component
public class PeopleService {
    @Value("${pcs.normalize.aop.name:World}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        PeopleService peopleService = new PeopleService();
        //peopleService.setName("hello world");
        System.out.println(peopleService.getName());
    }
}
