package com.safetynet.alerts.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testGetFireStationByStationWithStationList() {
        assertEquals(3, fireStationDao.getFireStationByStation(List.of(1)).size());
    }

    @Test
    void testGetFireStationByStationWithStationNb() {
        assertEquals(3, fireStationDao.getFireStationByStation(1).size());
    }

    @Test
    void testGetFireStationByAddress() {
        Optional<FireStation> fireStationOptional = fireStationDao.getFireStationByAddress("908 73rd St");
        fireStationOptional.ifPresent(fireStation -> assertEquals(1, fireStation.getStation()));
    }

    @Test
    void testGetFireStationByAddressWithWrongAddress() {
        Optional<FireStation> fireStationOptional = fireStationDao.getFireStationByAddress("wrong address");
        assertTrue(fireStationOptional.isEmpty());
    }

    @Test
    void testGetFireStationAddressesByStation() {
        assertEquals(Arrays.asList("644 Gershwin Cir", "908 73rd St", "947 E. Rose Dr"), fireStationDao.getFireStationAddressesByStation(1));
    }
}
