package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.person.DeleteBody;
import com.safetynet.alerts.dto.person.PostBody;
import com.safetynet.alerts.dto.person.PutBody;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    PersonDao personDao;

    @InjectMocks
    PersonServiceImpl personService;

    @Test
    void testPostPersonOk() {
        PostBody body = new PostBody("John", "Doe", "123 Street", "City", "12345", "123-456-7890", "johndoe@example.com");
        when(personDao.getPersonByName("John", "Doe")).thenReturn(Optional.empty());
        when(personDao.addPerson(any(Person.class))).thenReturn(true);

        assertTrue(personService.postPerson(body));

        verify(personDao).addPerson(any(Person.class));
    }

    @Test
    void testPostPersonKo() {
        PostBody body = new PostBody("John", "Doe", "123 Street", "City", "12345", "123-456-7890", "johndoe@example.com");
        when(personDao.getPersonByName("John", "Doe")).thenReturn(Optional.of(new Person()));

        assertFalse(personService.postPerson(body));
        verify(personDao, never()).addPerson(any(Person.class));
    }

    @Test
    void testUpdatePersonOk() {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setEmail("johndoe@me.com");
        Person existingPerson = new Person("John", "Doe", "123 Street", "City", "12345", "123-456-7890", "johndoe@example.com");

        when(personDao.getPersonByName("John", "Doe")).thenReturn(Optional.of(existingPerson));

        assertNotNull(personService.updatePerson(body));
        verify(personDao).updatePerson(any(Person.class));
    }

    @Test
    void testUpdatePersonDoesNotExist() {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setEmail("johndoe@me.com");

        when(personDao.getPersonByName("John", "Doe")).thenReturn(Optional.empty());

        assertNull(personService.updatePerson(body));
        verify(personDao, never()).updatePerson(any(Person.class));
    }

    @Test
    void testUpdatePersonThrowException() {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setEmail("johndoe@me.com");
        Person existingPerson = new Person("John", "Doe", "123 Street", "City", "12345", "123-456-7890", "johndoe@example.com");

        when(personDao.getPersonByName("John", "Doe")).thenReturn(Optional.of(existingPerson));
        doThrow(new RuntimeException("DataBase error")).when(personDao).updatePerson(any(Person.class));

        assertNull(personService.updatePerson(body));
    }

    @Test
    void testDeletePersonOk() {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        Person existingPerson = new Person("John", "Doe", "123 Street", "City", "12345", "123-456-7890", "johndoe@example.com");

        when(personDao.getPersonByName("John", "Doe")).thenReturn(Optional.of(existingPerson));
        when(personDao.deletePerson(existingPerson)).thenReturn(true);

        assertTrue(personService.deletePerson(body));
        verify(personDao).deletePerson(any(Person.class));
    }

    @Test
    void testDeletePersonDoesNotExist() {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        when(personDao.getPersonByName("John", "Doe")).thenReturn(Optional.empty());

        assertFalse(personService.deletePerson(body));
        verify(personDao, never()).deletePerson(any(Person.class));
    }
}
