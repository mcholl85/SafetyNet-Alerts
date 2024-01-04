package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;

import java.util.List;

public interface PersonService {
    StationInfoDto getPersonsInfoByFireStation(Integer stationNumber);

    PhoneListDto getPhoneNumbersByFireStation(Integer fireStationNumber);

    List<PersonInfoDto> getPersonInfoByName(String firstName, String lastName);

    List<String> getEmailByCity(String city);

    List<StationsDto> getPersonInfoByStations(List<Integer> nbStationList);

    FirePersonDto getPersonsByFireStation(String address);
}
