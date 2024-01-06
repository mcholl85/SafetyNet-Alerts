package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;

import java.util.List;

public interface AlertsService {
    StationInfoDto getPersonsInfoByFireStation(Integer stationNumber);

    ChildrenByAddressDto getChildrenByAddress(String address);

    PhoneListDto getPhoneNumbersByFireStation(Integer fireStationNumber);

    List<PersonDto> getPersonInfoByName(String firstName, String lastName);

    List<String> getEmailByCity(String city);

    List<StationsDto> getPersonInfoByStations(List<Integer> nbStationList);

    FirePersonDto getPersonsByFireStation(String address);
}
