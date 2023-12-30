package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;

import java.util.List;

public interface PersonDao {
    void addPerson(Person person);

    List<Person> getPersons(String firstName, String lastName);

    List<Person> getAllPersons();

    void updatePerson(Person person);

    void deletePerson(Person person);
}
