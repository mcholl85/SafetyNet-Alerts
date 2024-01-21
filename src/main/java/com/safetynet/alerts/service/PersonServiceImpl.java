package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.alerts.PersonDto;
import com.safetynet.alerts.dto.person.DeleteBody;
import com.safetynet.alerts.dto.person.PostBody;
import com.safetynet.alerts.dto.person.PutBody;
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

    // TODO getALL

    @Override
    public boolean postPerson(PostBody body) {
        Optional<Person> optionalPerson = personDao.getPersonByName(body.getFirstName(), body.getLastName());

        if (optionalPerson.isPresent()) {
            log.error("Creation person error : Person already created");
            return false;
        }
        Person person = new Person(body.getFirstName(), body.getLastName(), body.getAddress(), body.getCity(), body.getZip(), body.getPhone(), body.getEmail());

        return personDao.addPerson(person);
    }

    @Override
    public PersonDto updatePerson(PutBody body) {
        Optional<Person> optionalPerson = personDao.getPersonByName(body.getFirstName(), body.getLastName());

        if (optionalPerson.isEmpty()) {
            log.error("Update person exception : person does not exist.");
            return null;
        }

        Person personToUpdated = getPersonToUpdated(body, optionalPerson.get());

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
    public boolean deletePerson(DeleteBody body) {
        Optional<Person> optionalPerson = personDao.getPersonByName(body.getFirstName(), body.getLastName());

        if (optionalPerson.isEmpty()) {
            log.error("Delete person exception : person does not exist.");
            return false;
        }

        Person personToDelete = optionalPerson.get();

        return personDao.deletePerson(personToDelete);
    }

    private static Person getPersonToUpdated(PutBody body, Person person) {
        if (StringUtils.isNotEmpty(body.getAddress())) {
            person.setAddress(body.getAddress());
        }
        if (StringUtils.isNotEmpty(body.getCity())) {
            person.setCity(body.getCity());
        }
        if (StringUtils.isNotEmpty(body.getZip())) {
            person.setZip(body.getZip());
        }
        if (StringUtils.isNotEmpty(body.getPhone())) {
            person.setPhone(body.getPhone());
        }
        if (StringUtils.isNotEmpty(body.getEmail())) {
            person.setEmail(body.getEmail());

        }
        return person;
    }
}
