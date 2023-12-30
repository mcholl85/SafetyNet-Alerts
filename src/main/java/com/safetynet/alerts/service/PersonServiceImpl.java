package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.PersonInfoDto;
import com.safetynet.alerts.dto.PersonInfoStationDto;
import com.safetynet.alerts.dto.StationsDto;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
            List<PersonInfoStationDto> personInfoStationDtoList = new ArrayList<>();

            String address = station.getAddress();
            stationsDto.setAddress(address);

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
            
            stationsDto.setPersonInfoStationDtoList(personInfoStationDtoList);
            stationsDtoList.add(stationsDto);
        });

        return stationsDtoList;
    }
}
