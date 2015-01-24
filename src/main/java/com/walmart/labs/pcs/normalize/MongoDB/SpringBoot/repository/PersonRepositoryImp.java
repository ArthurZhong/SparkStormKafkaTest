package com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.repository;

import java.util.List;

import com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.entity.Person;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by pzhong1 on 1/23/15.
 */
public class PersonRepositoryImp {
    static final Logger logger = LoggerFactory.getLogger(PersonRepositoryImp.class);
    @Autowired MongoTemplate mongoTemplate;
    public List<Person> getAllPersons() {
        List<Person> results = mongoTemplate.findAll(Person.class);
        logger.info("Total amount of persons: {}", results.size());
        logger.info("Results: {}", results);
        return results;
    }

    public Person searchPerson(String id) {
        Person person = mongoTemplate.findById(id, Person.class);
        return person;
    }
    public void insertPersonWithNameJohnAndRandomAge() { //get random age between 1 and 100
        double age = Math.ceil(Math.random() * 100);
        Person p = new Person("John", (int) age);
        mongoTemplate.insert(p);
    }

    public void insertPersonWithNameJohnAndRandomAge(Person person) { //get random age between 1 and 100
        mongoTemplate.insert(person);
    }
    /** * Create a {@link Person} collection if the collection does not already exists */
    public void createPersonCollection() {
        if (!mongoTemplate.collectionExists(Person.class)) {
            mongoTemplate.createCollection(Person.class); }
    }
    /** * Drops the {@link Person} collection if the collection does already exists */
    public void dropPersonCollection() {
        if (mongoTemplate.collectionExists(Person.class)) {
            mongoTemplate.dropCollection(Person.class);
        }
    }
}
