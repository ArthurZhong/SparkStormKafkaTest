package com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.service;

import com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.entity.Person;
import com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.repository.PersonRepository;
import com.walmart.labs.pcs.normalize.MongoDB.SpringBoot.repository.PersonRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by pzhong1 on 1/23/15.
 */
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    public Person searchPerson(String id){
        return personRepository.findOne(id);
    }

    public void insertPersonWithNameJohnAndRandomAge(Person person){
        personRepository.save(person);
    }

    public void dropPersonCollection() {
        personRepository.deleteAll();
    }
}
