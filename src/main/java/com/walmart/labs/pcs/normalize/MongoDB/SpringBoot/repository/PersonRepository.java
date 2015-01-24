package com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.repository;

import com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by pzhong1 on 1/23/15.
 */
public interface PersonRepository extends MongoRepository<Person, String> {
    public Person findByFirstName(String name);
    public List<Person> findByLastName(String lastName);
}
