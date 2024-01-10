package com.safetynet.alerts.dao;

import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonDaoImpl implements PersonDao {
    private List<Person> personList;

    @Autowired
    public PersonDaoImpl(DataDto data) {
        this.personList = data.getPersons();
    }

    @Override
    public boolean addPerson(Person person) {
        return this.personList.add(person);
    }

    @Override
    public Optional<Person> getPersonByName(String firstName, String lastName) {
        return this.personList.stream().filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).findFirst();
    }

    @Override
    public List<Person> getPersonsByName(String firstName, String lastName) {
        return this.personList.stream().filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).toList();
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {
        return this.personList.stream().filter(person -> person.getAddress().equals(address)).toList();
    }

    @Override
    public List<String> getEmailsByCity(String city) {
        return this.personList.stream().filter(person -> person.getCity().equals(city)).map(Person::getEmail).toList();
    }

    @Override
    public List<Person> getAllPersons() {
        return this.personList;
    }

    @Override
    public void updatePerson(Person person) {
        this.personList = this.personList.stream().map(p -> {
            if (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()))
                return person;
            return p;
        }).toList();
    }

    @Override
    public boolean deletePerson(Person person) {
        return this.personList.remove(person);
    }
}
