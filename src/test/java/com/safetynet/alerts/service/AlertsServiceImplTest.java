package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.alerts.*;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertsServiceImplTest {
    @Mock
    PersonDao personDao;
    @Mock
    MedicalRecordDao medicalRecordDao;
    @Mock
    FireStationDao fireStationDao;

    @InjectMocks
    AlertsServiceImpl alertsService;

    @Test
    void testGetPersonsInfoByFireStation() {
        Integer stationNb = 1;
        List<String> addresses = Arrays.asList("644 Gershwin Cir", "908 73rd St");
        Person peter = new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person reginold = new Person("Reginold", "Walker", "908 73rd St", "Culver", "97451", "841-874-8547", "reg@email.com");

        MedicalRecord medicalRecordPeter = new MedicalRecord("Peter", "Duncan", LocalDate.of(2000, 9, 6), Collections.emptyList(), List.of("shellfish"));
        MedicalRecord medicalRecordReginold = new MedicalRecord("Reginold", "Walker", LocalDate.of(1979, 8, 30), List.of("thradox:700mg"), List.of("illisoxian"));

        when(fireStationDao.getFireStationAddressesByStation(stationNb)).thenReturn(addresses);
        when(personDao.getPersonsByAddress("644 Gershwin Cir")).thenReturn(List.of(peter));
        when(personDao.getPersonsByAddress("908 73rd St")).thenReturn(List.of(reginold));
        when(medicalRecordDao.getMedicalRecord("Peter", "Duncan")).thenReturn(Optional.of(medicalRecordPeter));
        when(medicalRecordDao.getMedicalRecord("Reginold", "Walker")).thenReturn(Optional.of(medicalRecordReginold));

        StationInfoDto stationInfoDto = alertsService.getPersonsInfoByFireStation(stationNb);

        assertEquals(2, stationInfoDto.getAdultCount());
        assertEquals(0, stationInfoDto.getChildrenCount());
        assertEquals(2, stationInfoDto.getPersons().size());
        assertEquals("841-874-6512", stationInfoDto.getPersons().get(0).getPhone());
    }

    @Test
    void testGetChildrenByAddress() {
        String address = "947 E. Rose Dr";
        List<Person> personList = Arrays.asList(new Person("Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"), new Person("Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"), new Person("Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"));
        MedicalRecord kendrik = new MedicalRecord("Kendrik", "Stelzer", LocalDate.of(2014, 3, 6), null, null);
        MedicalRecord brian = new MedicalRecord("Brian", "Stelzer", LocalDate.of(1975, 12, 6), null, null);
        MedicalRecord shawna = new MedicalRecord("Shawna", "Stelzer", LocalDate.of(1980, 7, 8), null, null);

        when(personDao.getPersonsByAddress(address)).thenReturn(personList);
        when(medicalRecordDao.getMedicalRecord("Kendrik", "Stelzer")).thenReturn(Optional.of(kendrik));
        when(medicalRecordDao.getMedicalRecord("Brian", "Stelzer")).thenReturn(Optional.of(brian));
        when(medicalRecordDao.getMedicalRecord("Shawna", "Stelzer")).thenReturn(Optional.of(shawna));

        ChildrenByAddressDto childrenByAddressDto = alertsService.getChildrenByAddress(address);
        assertEquals("Kendrik", childrenByAddressDto.getChildren().get(0).getFirstName());
        assertEquals(1, childrenByAddressDto.getChildren().size());
        assertEquals(2, childrenByAddressDto.getPersons().size());
    }

    @Test
    void testGetPhoneNumbersByFireStation() {
        Integer stationNb = 3;
        List<FireStation> fireStationList = List.of(new FireStation("748 Townings Dr", 3));
        List<Person> personList = Arrays.asList(new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com"), new Person("Foster", "Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544", "jaboyd@email.com"));

        when(fireStationDao.getFireStationByStation(stationNb)).thenReturn(fireStationList);
        when(personDao.getPersonsByAddress("748 Townings Dr")).thenReturn(personList);

        PhoneListDto phoneListDto = alertsService.getPhoneNumbersByFireStation(stationNb);
        assertEquals(2, phoneListDto.getPhoneList().size());
        assertTrue(phoneListDto.getPhoneList().containsAll(Arrays.asList("clivfd@ymail.com", "jaboyd@email.com")));
    }

    @Test
    void testGetPersonsByFireStation() {
        String address = "748 Townings Dr";
        Optional<FireStation> fireStationOptional = Optional.of(new FireStation("748 Townings Dr", 3));
        List<Person> personList = List.of(new Person("Foster",
                "Shepard",
                address,
                "Culver",
                "97451",
                "841-874-6544",
                "jaboyd@email.com"));
        MedicalRecord medicalRecord = new MedicalRecord("Foster", "Shepard", LocalDate.of(1980, 8, 1), Collections.emptyList(), Collections.emptyList());

        when(fireStationDao.getFireStationByAddress(address)).thenReturn(fireStationOptional);
        when(personDao.getPersonsByAddress(address)).thenReturn(personList);
        when(medicalRecordDao.getMedicalRecord("Foster", "Shepard")).thenReturn(Optional.of(medicalRecord));

        FirePersonDto firePersonDto = alertsService.getPersonsByFireStation(address);

        assertEquals(3, firePersonDto.getStation());
        assertEquals(1, firePersonDto.getPersons().size());
        assertEquals(44, firePersonDto.getPersons().get(0).getAge());
    }

    @Test
    void testGetPersonsByFireStationWithNoReturnStation() {
        String address = "748 Townings Dr";
        Optional<FireStation> station = Optional.empty();

        when(fireStationDao.getFireStationByAddress(address)).thenReturn(station);

        FirePersonDto firePersonDto = alertsService.getPersonsByFireStation(address);

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
        when(medicalRecordDao.getMedicalRecord(firstName, lastName)).thenReturn(Optional.of(medicalRecord));

        List<PersonDto> personDtoList = alertsService.getPersonInfoByName(firstName, lastName);
        assertEquals(firstName, personDtoList.get(0).getFirstName());
        assertEquals("947 E. Rose Dr", personDtoList.get(0).getAddress());
    }

    @Test
    void testGetEmailByCity() {
        String city = "Paris";
        List<String> emails = Arrays.asList("jaboyd@email.com", "drk@email.com");

        when(personDao.getEmailsByCity(city)).thenReturn(emails);

        assertTrue(alertsService.getEmailByCity(city).containsAll(emails));
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
        when(medicalRecordDao.getMedicalRecord("Foster", "Shepard")).thenReturn(Optional.of(medicalRecord));

        List<StationsDto> stationsDtoList = alertsService.getPersonInfoByStations(nbStationsList);
        assertEquals(1, stationsDtoList.size());
        assertEquals(1, stationsDtoList.get(0).getPersons().size());
        assertEquals(44, stationsDtoList.get(0).getPersons().get(0).getAge());
        assertEquals("748 Townings Dr", stationsDtoList.get(0).getAddress());
        assertEquals("841-874-6544", stationsDtoList.get(0).getPersons().get(0).getPhone());
    }
}
