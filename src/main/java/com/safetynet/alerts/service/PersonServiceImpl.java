package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.alerts.PersonDto;
import com.safetynet.alerts.dto.person.DeleteParams;
import com.safetynet.alerts.dto.person.PostParams;
import com.safetynet.alerts.dto.person.PutParams;
import com.safetynet.alerts.model.Person;
import io.micrometer.common.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean postPerson(PostParams params) {
        Optional<Person> optionalPerson = personDao.getPersonByName(params.getFirstName(), params.getLastName());

        if (optionalPerson.isPresent()) {
            log.error("Creation person error : Person already created");
            return false;
        }
        Person person = new Person(params.getFirstName(), params.getLastName(), params.getAddress(), params.getCity(), params.getZip(), params.getPhone(), params.getEmail());

        return personDao.addPerson(person);
    }

    @Override
    public PersonDto updatePerson(PutParams params) {
        Optional<Person> optionalPerson = personDao.getPersonByName(params.getFirstName(), params.getLastName());

        if (optionalPerson.isEmpty()) {
            log.error("Update person exception : person does not exist.");
            return null;
        }

        Person personToUpdated = getPersonToUpdated(params, optionalPerson.get());

        try {
            personDao.updatePerson(personToUpdated);
        } catch (Exception e) {
            log.error("Update person exception : " + e.getMessage());
            return null;
        }

        PersonDto updatedPersonDto = new PersonDto();

        updatedPersonDto.setFirstName(personToUpdated.getFirstName());
        updatedPersonDto.setLastName(personToUpdated.getLastName());
        updatedPersonDto.setAddress(personToUpdated.getAddress());
        updatedPersonDto.setCity(personToUpdated.getCity());
        updatedPersonDto.setZip(personToUpdated.getZip());
        updatedPersonDto.setPhone(personToUpdated.getPhone());
        updatedPersonDto.setEmail(personToUpdated.getEmail());

        return updatedPersonDto;
    }

    @Override
    public boolean deletePerson(DeleteParams params) {
        Optional<Person> optionalPerson = personDao.getPersonByName(params.getFirstName(), params.getLastName());

        if (optionalPerson.isEmpty()) {
            log.error("Delete person exception : person does not exist.");
            return false;
        }

        Person personToDelete = optionalPerson.get();

        return personDao.deletePerson(personToDelete);
    }

    private static Person getPersonToUpdated(PutParams params, Person person) {
        if (StringUtils.isNotEmpty(params.getAddress())) {
            person.setAddress(params.getAddress());
        }
        if (StringUtils.isNotEmpty(params.getCity())) {
            person.setCity(params.getCity());
        }
        if (StringUtils.isNotEmpty(params.getZip())) {
            person.setZip(params.getZip());
        }
        if (StringUtils.isNotEmpty(params.getPhone())) {
            person.setPhone(params.getPhone());
        }
        if (StringUtils.isNotEmpty(params.getEmail())) {
            person.setEmail(params.getEmail());

        }
        return person;
    }
}
