package com.safetynet.alerts.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicalRecordDaoTest {
    MedicalRecordDaoImpl medicalRecordDao;

    @BeforeEach
    void setup() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonData = new String(Files.readAllBytes(Paths.get("src/test/resources/mockData.json")));
        DataDto dataDto = objectMapper.readValue(jsonData, DataDto.class);

        medicalRecordDao = new MedicalRecordDaoImpl(dataDto);
    }

    @Test
    void testGetMedicalRecordIsPresent() {
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordDao.getMedicalRecord("Sophia", "Zemicks");

        assertTrue(medicalRecordDao.getMedicalRecord("Sophia", "Zemicks").isPresent());
        medicalRecordOptional.ifPresent(medicalRecord -> assertEquals(LocalDate.of(1988, 3, 6), medicalRecord.getBirthdate()));
    }

    @Test
    void testGetMedicalRecordIsEmpty() {
        assertTrue(medicalRecordDao.getMedicalRecord("John", "Doe").isEmpty());
    }

    @Test
    void testAddMedicalRecord() {
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", LocalDate.of(1929, 1, 1), Collections.emptyList(), Collections.emptyList());

        assertTrue(medicalRecordDao.addMedicalRecord(medicalRecord));
        assertTrue(medicalRecordDao.getMedicalRecord("John", "Doe").isPresent());
    }

    @Test
    void testUpdateMedicalRecord() {
        MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecord("Sophia", "Zemicks").get();
        medicalRecord.setAllergies(List.of("cat"));

        medicalRecordDao.updateMedicalRecord(medicalRecord);

        MedicalRecord updatedRecord = medicalRecordDao.getMedicalRecord("Sophia", "Zemicks").get();
        assertTrue(updatedRecord.getAllergies().contains("cat"));
    }

    @Test
    void testDeleteMedicalRecord() {
        MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecord("Sophia", "Zemicks").get();

        assertTrue(medicalRecordDao.deleteMedicalRecord(medicalRecord));
        assertTrue(medicalRecordDao.getMedicalRecord("Sophia", "Zemicks").isEmpty());
    }
}
