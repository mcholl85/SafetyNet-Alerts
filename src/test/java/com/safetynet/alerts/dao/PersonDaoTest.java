package com.safetynet.alerts.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dao.impl.PersonDaoImpl;
import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonDaoTest {

    PersonDaoImpl personDao;

    @BeforeEach
    void setup() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonData = new String(Files.readAllBytes(Paths.get("src/test/resources/mockData.json")));
        DataDto dataDto = objectMapper.readValue(jsonData, DataDto.class);

        personDao = new PersonDaoImpl(dataDto);
    }

    @Test
    void testGetPersonByNameIsPresent() {
        assertTrue(personDao.getPersonByName("John", "Boyd").isPresent());
    }

    @Test
    void testGetPersonByNameIsEmpty() {
        assertTrue(personDao.getPersonByName("John", "Doe").isEmpty());
    }

    @Test
    void testGetAllPerson() {
        assertEquals(23, personDao.getAllPersons().size());
    }

    @Test
    void testGetPersonsByName() {
        List<Person> personList = personDao.getPersonsByName("John", "Boyd");

        assertEquals(1, personList.size());
        assertEquals("John", personList.get(0).getFirstName());
    }

    @Test
    void testAddPerson() {
        Person person = new Person("John", "Doe", "951 LoneTree Rd", "Culver", "97451", "841-874-6580", "johndoe@me.com");

        personDao.addPerson(person);
        List<Person> personList = personDao.getAllPersons();

        assertEquals(24, personList.size());
        assertEquals("Doe", personList.get(personList.size() - 1).getLastName());
    }

    @Test
    void testGetPersonsByAddress() {
        assertEquals(5, personDao.getPersonsByAddress("1509 Culver St").size());
    }

    @Test
    void testGetEmailsByCity() {
        List<String> emails = personDao.getEmailsByCity("Culver");
        assertEquals(23, emails.size());
        assertTrue(emails.contains("zarc@email.com"));
    }

    @Test
    void testUpdatePerson() {
        Person person = personDao.getPersonsByName("Eric", "Cadigan").get(0);
        person.setCity("Marseille");

        personDao.updatePerson(person);
        Person updatedPerson = personDao.getPersonsByName("Eric", "Cadigan").get(0);

        assertEquals("Marseille", updatedPerson.getCity());
    }

    @Test
    void testDeletePerson() {
        Person person = personDao.getPersonsByName("Eric", "Cadigan").get(0);

        personDao.deletePerson(person);
        List<Person> personList = personDao.getAllPersons();

        assertEquals(22, personList.size());
    }
}
