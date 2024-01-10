package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.alerts.*;
import com.safetynet.alerts.service.AlertsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class AlertsController {
    private final AlertsService alertsService;

    @Autowired
    public AlertsController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<StationInfoDto> getPersonsInfoByFireStation(@RequestParam("stationNumber") Integer stationNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(alertsService.getPersonsInfoByFireStation(stationNumber));
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildrenByAddressDto> getChildrenByAddress(@RequestParam("address") String address) {
        return ResponseEntity.status(HttpStatus.OK).body(alertsService.getChildrenByAddress(address));
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneListDto> getPhoneNumberByFireStation(@RequestParam("firestation") Integer fireStationNumber) {
        PhoneListDto phoneListDto = alertsService.getPhoneNumbersByFireStation((fireStationNumber));

        if (phoneListDto.getPhoneList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(phoneListDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(phoneListDto);
    }

    @GetMapping("/fire")
    public ResponseEntity<FirePersonDto> getPersonsByFireStation(@RequestParam("address") String address) {
        return ResponseEntity.status(HttpStatus.OK).body(alertsService.getPersonsByFireStation(address));
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<StationsDto>> getPersonInfoByStations(@RequestParam("stations") List<Integer> nbStationList) {
        return ResponseEntity.status(HttpStatus.OK).body(alertsService.getPersonInfoByStations(nbStationList));
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonDto>> getPersonInfoByName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(alertsService.getPersonInfoByName(firstName, lastName));
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailBy(@RequestParam("city") String city) {
        return ResponseEntity.status(HttpStatus.OK).body(alertsService.getEmailByCity(city));
    }
}
