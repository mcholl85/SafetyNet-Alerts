package com.safetynet.alerts.dao;

import com.safetynet.alerts.dto.DataDto;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonDaoImpl implements PersonDao {
    private List<Person> personList;

    @Autowired
    public PersonDaoImpl(DataDto data) {
        this.personList = data.getPersons();
    }

    @Override
    public void addPerson(Person person) {
        this.personList.add(person);
    }

    @Override
    public List<Person> getPersonsByName(String firstName, String lastName) {
        return this.personList.stream().filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).toList();
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
        }).collect(Collectors.toList());
    }

    @Override
    public void deletePerson(Person person) {
        this.personList.remove(person);
    }
}
