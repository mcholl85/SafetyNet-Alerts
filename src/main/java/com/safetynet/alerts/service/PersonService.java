package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FirePersonDto;
import com.safetynet.alerts.dto.PersonInfoDto;
import com.safetynet.alerts.dto.StationsDto;

import java.util.List;

public interface PersonService {
    List<PersonInfoDto> getPersonInfoByName(String firstName, String lastName);

    List<String> getEmailByCity(String city);

    List<StationsDto> getPersonInfoByStations(List<Integer> nbStationList);

    FirePersonDto getPersonsByFireStation(String address);
}
