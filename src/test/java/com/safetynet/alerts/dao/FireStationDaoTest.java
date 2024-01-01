package com.safetynet.alerts.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dto.DataDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FireStationDaoTest {
    FireStationDaoImpl fireStationDao;

    @BeforeAll
    void setup() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonData = new String(Files.readAllBytes(Paths.get("src/test/resources/mockData.json")));
        DataDto dataDto = objectMapper.readValue(jsonData, DataDto.class);

        fireStationDao = new FireStationDaoImpl(dataDto);
    }

    @Test
    void testGetFireStationByStation() {
        assertEquals(3, fireStationDao.getFireStationByStation(List.of(1)).size());
    }
}
