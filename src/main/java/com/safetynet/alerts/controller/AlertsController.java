package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.service.PersonService;
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
    private final PersonService personService;

    @Autowired
    public AlertsController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("firestation")
    public ResponseEntity<StationInfoDto> getPersonsInfoByFireStation(@RequestParam("stationNumber") Integer stationNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonsInfoByFireStation(stationNumber));
    }

    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildrenByAddress(@RequestParam("address") String address) {
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/phoneAlert")

    public ResponseEntity<PhoneListDto> getPhoneNumberByFireStation(@RequestParam("firestation") Integer fireStationNumber) {
        PhoneListDto phoneListDto = personService.getPhoneNumbersByFireStation((fireStationNumber));

        if (phoneListDto.getPhoneList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(phoneListDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(phoneListDto);
    }

    @GetMapping("/fire")

    public ResponseEntity<FirePersonDto> getPersonsByFireStation(@RequestParam("address") String address) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonsByFireStation(address));
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<StationsDto>> getPersonInfoByStations(@RequestParam("stations") List<Integer> nbStationList) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonInfoByStations(nbStationList));
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDto>> getPersonInfoByName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonInfoByName(firstName, lastName));
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailBy(@RequestParam("city") String city) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getEmailByCity(city));
    }
}
