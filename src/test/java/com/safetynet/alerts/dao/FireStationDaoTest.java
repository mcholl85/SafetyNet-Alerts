package com.safetynet.alerts.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dao.impl.FireStationDaoImpl;
import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
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

    @Test
    void testGetFireStationIsPresent() {
        assertTrue(fireStationDao.getFireStation("947 E. Rose Dr", 1).isPresent());
    }

    @Test
    void testGetFireStationIsEmpty() {
        assertTrue(fireStationDao.getFireStation("947 E. Rose Dr", 10).isEmpty());
    }

    @Test
    void testAddFireStation() {
        FireStation fireStation = new FireStation("947 E. Rose Dr", 1);

        assertTrue(fireStationDao.addFireStation(fireStation));
        assertTrue(fireStationDao.getFireStation("947 E. Rose Dr", 1).isPresent());
    }

    @Test
    void testUpdateFireStation() {
        FireStation fireStation = fireStationDao.getFireStation("947 E. Rose Dr", 1).get();
        fireStation.setStation(3);

        fireStationDao.updateFireStation(fireStation);
        FireStation updatedFireStation = fireStationDao.getFireStation("947 E. Rose Dr", 3).get();
        assertEquals(3, updatedFireStation.getStation());
    }


    @Test
    void testDeleteFireStation() {
        FireStation fireStation = fireStationDao.getFireStation("947 E. Rose Dr", 1).get();

        assertTrue(fireStationDao.deleteFireStation(fireStation));
        assertTrue(fireStationDao.getFireStation("947 E. Rose Dr", 1).isEmpty());
    }
}
