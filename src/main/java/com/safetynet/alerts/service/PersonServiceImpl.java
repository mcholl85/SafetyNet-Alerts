package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dto.PersonInfoDto;
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

    @Autowired
    public PersonServiceImpl(PersonDao personDao, MedicalRecordDao medicalRecordDao) {
        this.personDao = personDao;
        this.medicalRecordDao = medicalRecordDao;
    }

    @Override
    public List<PersonInfoDto> getPersonInfoByName(String firstName, String lastName) {
        List<PersonInfoDto> personInfoDtoList = new ArrayList<>();

        List<Person> personList = personDao.getPersonsByName(firstName, lastName);
        List<MedicalRecord> medicalRecordList = medicalRecordDao.getMedicalRecords(firstName, lastName);

        for (int i = 0; i < personList.size(); i++) {
            PersonInfoDto infos = new PersonInfoDto();
            int age = LocalDate.now().getYear() - medicalRecordList.get(i).getBirthdate().getYear();

            infos.setFirstName(personList.get(i).getFirstName());
            infos.setLastName(personList.get(i).getLastName());
            infos.setAge(age);
            infos.setAddress(personList.get(i).getAddress());
            infos.setEmail(personList.get(i).getEmail());
            infos.setMedications(medicalRecordList.get(i).getMedications());
            infos.setAllergies(medicalRecordList.get(i).getAllergies());

            personInfoDtoList.add(infos);
        }

        return personInfoDtoList;
    }

    public List<String> getEmailByCity(String city) {
        return personDao.getEmailsByCity(city);
    }
}
