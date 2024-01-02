package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FirePersonDto;
import com.safetynet.alerts.dto.PersonInfoDto;
import com.safetynet.alerts.dto.PhoneListDto;
import com.safetynet.alerts.dto.StationsDto;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertsController {
    private final PersonService personService;

    @Autowired
    public AlertsController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneListDto> getPhoneNumberByFireStation(@RequestParam("firestation") Integer fireStationNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPhoneNumbersByFireStation(fireStationNumber));
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
