package com.safetynet.alerts.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicalRecordDaoTest {
    MedicalRecordDaoImpl medicalRecordDao;

    @BeforeAll
    void setup() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonData = new String(Files.readAllBytes(Paths.get("src/test/resources/mockData.json")));
        DataDto dataDto = objectMapper.readValue(jsonData, DataDto.class);

        medicalRecordDao = new MedicalRecordDaoImpl(dataDto);
    }

    @Test
    void testGetMedicalRecord() {
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordDao.getMedicalRecord("Sophia", "Zemicks");
        
        medicalRecordOptional.ifPresent(medicalRecord -> assertEquals(LocalDate.of(1988, 3, 6), medicalRecord.getBirthdate()));
    }

}
