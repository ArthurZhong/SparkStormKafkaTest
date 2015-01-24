package com.walmart.labs.pcs.normalize.MongoDB.SpringBoot;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by pzhong1 on 1/23/15.
 */
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons(){
        return personRepository.getAllPersons();
    }

    public Person searchPerson(String id){
        return personRepository.searchPerson(id);
    }

    public void insertPersonWithNameJohnAndRandomAge(Person person){
        personRepository.insertPersonWithNameJohnAndRandomAge(person);
    }

    public void dropPersonCollection() {
        personRepository.dropPersonCollection();
    }
}
