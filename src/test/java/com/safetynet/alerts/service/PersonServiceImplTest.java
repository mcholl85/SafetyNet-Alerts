package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.FirePersonDto;
import com.safetynet.alerts.dto.PersonInfoDto;
import com.safetynet.alerts.dto.StationsDto;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    PersonDao personDao;
    @Mock
    MedicalRecordDao medicalRecordDao;
    @Mock
    FireStationDao fireStationDao;

    @InjectMocks
    PersonServiceImpl personService;

    @Test
    void testGetPersonsByFireStation() {
        String address = "748 Townings Dr";
        Integer station = 3;
        List<Person> personList = List.of(new Person("Foster",
                "Shepard",
                address,
                "Culver",
                "97451",
                "841-874-6544",
                "jaboyd@email.com"));
        MedicalRecord medicalRecord = new MedicalRecord("Foster", "Shepard", LocalDate.of(1980, 8, 1), Collections.emptyList(), Collections.emptyList());

        when(fireStationDao.getFireStationByAddress(address)).thenReturn(station);
        when(personDao.getPersonsByAddress(address)).thenReturn(personList);
        when(medicalRecordDao.getMedicalRecord("Foster", "Shepard")).thenReturn(medicalRecord);

        FirePersonDto firePersonDto = personService.getPersonsByFireStation(address);

        assertEquals(3, firePersonDto.getStation());
        assertEquals(1, firePersonDto.getPersons().size());
        assertEquals(44, firePersonDto.getPersons().get(0).getAge());
    }

    @Test
    void testGetPersonsByFireStationWithNoReturnStation() {
        String address = "748 Townings Dr";
        Integer station = 0;

        when(fireStationDao.getFireStationByAddress(address)).thenReturn(station);

        FirePersonDto firePersonDto = personService.getPersonsByFireStation(address);

        assertEquals(0, firePersonDto.getStation());
        assertTrue(firePersonDto.getPersons().isEmpty());
    }


    @Test
    void testGetPersonInfoByName() {
        String firstName = "Brian";
        String lastName = "Stelzer";
        Person person = new Person(firstName, lastName, "947 E. Rose Dr", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, LocalDate.of(1975, 12, 6), Arrays.asList("ibupurin:200mg",
                "hydrapermazol:400mg"), List.of("nillacilan"));

        when(personDao.getPersonsByName(firstName, lastName)).thenReturn(List.of(person));
        when(medicalRecordDao.getMedicalRecord(firstName, lastName)).thenReturn(medicalRecord);

        List<PersonInfoDto> personInfoDtoList = personService.getPersonInfoByName(firstName, lastName);
        assertEquals(firstName, personInfoDtoList.get(0).getFirstName());
        assertEquals("947 E. Rose Dr", personInfoDtoList.get(0).getAddress());
    }

    @Test
    void testGetEmailByCity() {
        String city = "Paris";
        List<String> emails = Arrays.asList("jaboyd@email.com", "drk@email.com");

        when(personDao.getEmailsByCity(city)).thenReturn(emails);

        assertTrue(personService.getEmailByCity(city).containsAll(emails));
    }

    @Test
    void testGetPersonInfoByStations() {
        List<Integer> nbStationsList = Arrays.asList(1, 2);
        List<FireStation> fireStationList = List.of(new FireStation("748 Townings Dr", 3));
        List<Person> personList = List.of(new Person("Foster",
                "Shepard",
                "748 Townings Dr",
                "Culver",
                "97451",
                "841-874-6544",
                "jaboyd@email.com"));
        MedicalRecord medicalRecord = new MedicalRecord("Foster", "Shepard", LocalDate.of(1980, 8, 1), Collections.emptyList(), Collections.emptyList());

        when(fireStationDao.getFireStationByStation(nbStationsList)).thenReturn(fireStationList);
        when(personDao.getPersonsByAddress("748 Townings Dr")).thenReturn(personList);
        when(medicalRecordDao.getMedicalRecord("Foster", "Shepard")).thenReturn(medicalRecord);

        List<StationsDto> stationsDtoList = personService.getPersonInfoByStations(nbStationsList);
        assertEquals(1, stationsDtoList.size());
        assertEquals(1, stationsDtoList.get(0).getPersonInfoStationDtoList().size());
        assertEquals(44, stationsDtoList.get(0).getPersonInfoStationDtoList().get(0).getAge());
        assertEquals("748 Townings Dr", stationsDtoList.get(0).getAddress());
        assertEquals("841-874-6544", stationsDtoList.get(0).getPersonInfoStationDtoList().get(0).getPhone());
    }
}
