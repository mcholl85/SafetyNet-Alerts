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
    public StationInfoDto getPersonsInfoByFireStation(Integer stationNumber) {
        StationInfoDto stationInfoDto = new StationInfoDto();
        List<PersonDto> personDtoList = new ArrayList<>();

        int childrenCount = 0;
        int adultCount = 0;

        List<String> addresses = fireStationDao.getFireStationAddressesByStation(stationNumber);

        if (addresses.isEmpty()) {
            stationInfoDto.setPersons(personDtoList);
            stationInfoDto.setAdultCount(adultCount);
            stationInfoDto.setChildrenCount(childrenCount);

            return stationInfoDto;
        }

        for (String address : addresses) {
            List<Person> personList = personDao.getPersonsByAddress(address);

            for (Person person : personList) {
                PersonDto personDto = new PersonDto();

                personDto.setFirstName(person.getFirstName());
                personDto.setLastName(person.getLastName());
                personDto.setPhone(person.getPhone());
                personDto.setAddress(person.getAddress());

                personDtoList.add(personDto);

                MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecord(person.getFirstName(), person.getLastName());
                int age = LocalDate.now().compareTo(medicalRecord.getBirthdate());

                if (age > 18) {
                    adultCount += 1;
                } else {
                    childrenCount += 1;
                }
            }
        }

        stationInfoDto.setPersons(personDtoList);
        stationInfoDto.setChildrenCount(childrenCount);
        stationInfoDto.setAdultCount(adultCount);

        return stationInfoDto;
    }

    @Override
    public ChildrenByAddressDto getChildrenByAddress(String address) {
        ChildrenByAddressDto childrenByAddressDto = new ChildrenByAddressDto();
        List<PersonDto> childrenDtoList = new ArrayList<>();
        List<PersonDto> personDtoList = new ArrayList<>();

        this.personDao.getPersonsByAddress(address).forEach(person -> {
            PersonDto personDto = new PersonDto();
            MedicalRecord medicalRecord = this.medicalRecordDao.getMedicalRecord(person.getFirstName(), person.getLastName());
            int age = LocalDate.now().compareTo(medicalRecord.getBirthdate());

            if (age > 18) {
                personDto.setFirstName(person.getFirstName());
                personDto.setLastName(person.getLastName());
                personDto.setPhone(person.getPhone());

                personDtoList.add(personDto);
            } else {
                personDto.setFirstName(person.getFirstName());
                personDto.setLastName(person.getLastName());
                personDto.setAge(age);

                childrenDtoList.add(personDto);
            }
        });

        childrenByAddressDto.setChildren(childrenDtoList);
        childrenByAddressDto.setPersons(personDtoList);

        return childrenByAddressDto;
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
    public List<PersonDto> getPersonInfoByName(String firstName, String lastName) {
        List<PersonDto> personDtoList = new ArrayList<>();

        List<Person> personList = personDao.getPersonsByName(firstName, lastName);

        personList.forEach(person -> {
            PersonDto personDto = new PersonDto();
            MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecord(person.getFirstName(), person.getLastName());

            int age = LocalDate.now().compareTo(medicalRecord.getBirthdate());

            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setAge(age);
            personDto.setAddress(person.getAddress());
            personDto.setEmail(person.getEmail());
            personDto.setMedications(medicalRecord.getMedications());
            personDto.setAllergies(medicalRecord.getAllergies());

            personDtoList.add(personDto);
        });

        return personDtoList;
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
            stationsDto.setPersons(this.getPersonsInfoStation(address));

            stationsDtoList.add(stationsDto);
        });

        return stationsDtoList;
    }

    private List<PersonDto> getPersonsInfoStation(String address) {
        List<PersonDto> personDtoList = new ArrayList<>();

        personDao.getPersonsByAddress(address).forEach(person -> {
            PersonDto personDto = new PersonDto();
            MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecord(person.getFirstName(), person.getLastName());
            int age = LocalDate.now().compareTo(medicalRecord.getBirthdate());

            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setPhone(person.getPhone());
            personDto.setAge(age);
            personDto.setMedications(medicalRecord.getMedications());
            personDto.setAllergies(medicalRecord.getAllergies());

            personDtoList.add(personDto);
        });

        return personDtoList;
    }
}
