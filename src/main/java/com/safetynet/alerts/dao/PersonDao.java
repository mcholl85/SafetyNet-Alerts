package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    boolean addPerson(Person person);

    Optional<Person> getPersonByName(String firstName, String lastName);

    List<Person> getPersonsByName(String firstName, String lastName);

    List<Person> getPersonsByAddress(String address);

    List<String> getEmailsByCity(String city);

    List<Person> getAllPersons();

    void updatePerson(Person person);

    boolean deletePerson(Person person);
}
