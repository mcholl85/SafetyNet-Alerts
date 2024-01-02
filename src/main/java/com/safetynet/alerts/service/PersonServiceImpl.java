package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final MedicalRecordDao medicalRecordDao;
    private final FireStationDao fireStationDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao, MedicalRecordDao medicalRecordDao, FireStationDao fireStationDao) {
        this.personDao = personDao;
        this.medicalRecordDao = medicalRecordDao;
        this.fireStationDao = fireStationDao;
    }

    @Override
    public PhoneListDto getPhoneNumbersByFireStation(Integer fireStationNumber) {
        List<String> phoneNumberList = new ArrayList<>();

        fireStationDao.getFireStationByStation(fireStationNumber).forEach(fireStation -> {
            List<Person> personList = personDao.getPersonsByAddress(fireStation.getAddress());

            phoneNumberList.addAll(personList.stream().map(Person::getEmail).toList());
        });

        return new PhoneListDto(phoneNumberList);
    }

    @Override
    public FirePersonDto getPersonsByFireStation(String address) {
        FirePersonDto firePersonDto = new FirePersonDto();
        Integer station = fireStationDao.getFireStationByAddress(address);

        if (station == 0) {
            firePersonDto.setStation(0);
            firePersonDto.setPersons(Collections.emptyList());
            return firePersonDto;
        }

        firePersonDto.setStation(station);
        firePersonDto.setPersons(this.getPersonsInfoStation(address));

        return firePersonDto;
    }

    @Override
    public List<PersonInfoDto> getPersonInfoByName(String firstName, String lastName) {
        List<PersonInfoDto> personInfoDtoList = new ArrayList<>();

        List<Person> personList = personDao.getPersonsByName(firstName, lastName);

        personList.forEach(person -> {
            PersonInfoDto infos = new PersonInfoDto();
            MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecord(person.getFirstName(), person.getLastName());

            int age = LocalDate.now().compareTo(medicalRecord.getBirthdate());

            infos.setFirstName(person.getFirstName());
            infos.setLastName(person.getLastName());
            infos.setAge(age);
            infos.setAddress(person.getAddress());
            infos.setEmail(person.getEmail());
            infos.setMedications(medicalRecord.getMedications());
            infos.setAllergies(medicalRecord.getAllergies());

            personInfoDtoList.add(infos);
        });

        return personInfoDtoList;
    }

    public List<String> getEmailByCity(String city) {
        return personDao.getEmailsByCity(city);
    }

    public List<StationsDto> getPersonInfoByStations(List<Integer> nbStationList) {
        List<StationsDto> stationsDtoList = new ArrayList<>();
        List<FireStation> fireStationList = fireStationDao.getFireStationByStation(nbStationList);

        fireStationList.forEach(station -> {
            StationsDto stationsDto = new StationsDto();

            String address = station.getAddress();
            stationsDto.setAddress(address);
            stationsDto.setPersonInfoStationDtoList(this.getPersonsInfoStation(address));

            stationsDtoList.add(stationsDto);
        });

        return stationsDtoList;
    }

    private List<PersonInfoStationDto> getPersonsInfoStation(String address) {
        List<PersonInfoStationDto> personInfoStationDtoList = new ArrayList<>();

        personDao.getPersonsByAddress(address).forEach(person -> {
            PersonInfoStationDto personInfoStationDto = new PersonInfoStationDto();
            MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecord(person.getFirstName(), person.getLastName());
            int age = LocalDate.now().compareTo(medicalRecord.getBirthdate());

            personInfoStationDto.setFirstName(person.getFirstName());
            personInfoStationDto.setLastName(person.getLastName());
            personInfoStationDto.setPhone(person.getPhone());
            personInfoStationDto.setAge(age);
            personInfoStationDto.setMedications(medicalRecord.getMedications());
            personInfoStationDto.setAllergies(medicalRecord.getAllergies());

            personInfoStationDtoList.add(personInfoStationDto);
        });

        return personInfoStationDtoList;
    }
}
