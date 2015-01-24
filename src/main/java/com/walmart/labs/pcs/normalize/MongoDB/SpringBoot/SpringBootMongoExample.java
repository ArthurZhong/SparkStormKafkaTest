package com.walmart.labs.pcs.normalize.MongoDB.SpringBoot;

import com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.entity.Person;
import com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.service.PersonService;

/**
 * Created by pzhong1 on 1/23/15.
 */
public class SpringBootMongoExample {
    public static void main(String[] args) {
        PersonService personService = new PersonService();
        double age = Math.ceil(Math.random() * 100);
        Person p = new Person("John", (int) age);
        personService.insertPersonWithNameJohnAndRandomAge(p);

        System.out.println(personService.searchPerson(p.getPersonId()));
    }
}
