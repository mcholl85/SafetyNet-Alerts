package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FirePersonDto;
import com.safetynet.alerts.dto.PersonInfoDto;
import com.safetynet.alerts.dto.PhoneListDto;
import com.safetynet.alerts.dto.StationsDto;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlertsController.class)
class AlertsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @Test
    void testGetPhoneNumbersByFireStation() throws Exception {
        Integer stationNb = 1;

        when(personService.getPhoneNumbersByFireStation(stationNb)).thenReturn(new PhoneListDto(List.of("000000000")));
        mockMvc.perform(get("/phoneAlert").param("firestation", "1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(personService).getPhoneNumbersByFireStation(stationNb);
    }

    @Test
    void testGetPhoneNumbersByFireStationWithEmptyList() throws Exception {
        Integer stationNb = 1;

        when(personService.getPhoneNumbersByFireStation(stationNb)).thenReturn(new PhoneListDto(Collections.emptyList()));
        mockMvc.perform(get("/phoneAlert").param("firestation", "1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        verify(personService).getPhoneNumbersByFireStation(stationNb);
    }

    @Test
    void testGetPersonsByFireStation() throws Exception {
        String address = "951 LoneTree Rd";

        when(personService.getPersonsByFireStation(address)).thenReturn(new FirePersonDto());
        mockMvc.perform(get("/fire").param("address", address).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(personService).getPersonsByFireStation(address);
    }

    @Test
    void testGetPersonInfoByStations() throws Exception {
        List<Integer> stationNumbers = Arrays.asList(1, 2);

        when(personService.getPersonInfoByStations(stationNumbers)).thenReturn(List.of(new StationsDto()));
        mockMvc.perform(get("/flood/stations").param("stations", "1", "2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(personService).getPersonInfoByStations(stationNumbers);
    }

    @Test
    void testGetPersonInfoByName() throws Exception {
        List<PersonInfoDto> personInfoDtoList = List.of(new PersonInfoDto());

        when(personService.getPersonInfoByName("Brian", "Stelzer")).thenReturn(personInfoDtoList);
        mockMvc.perform(get("/personInfo").param("firstName", "Brian").param("lastName", "Stelzer").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(personService).getPersonInfoByName("Brian", "Stelzer");
    }

    @Test
    void testGetEmailBy() throws Exception {
        String city = "Paris";

        when(personService.getEmailByCity(city)).thenReturn(Arrays.asList("jaboyd@email.com", "drk@email.com"));
        mockMvc.perform(get("/communityEmail").param("city", city).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(personService).getEmailByCity(city);
    }
}
