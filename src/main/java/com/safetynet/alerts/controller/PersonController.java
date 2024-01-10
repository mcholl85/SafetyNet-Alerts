package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.alerts.PersonDto;
import com.safetynet.alerts.dto.person.DeleteParams;
import com.safetynet.alerts.dto.person.PostParams;
import com.safetynet.alerts.dto.person.PutParams;
import com.safetynet.alerts.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<HttpStatus> postPerson(@Valid @RequestBody PostParams params) {
        if (this.personService.postPerson(params)) {
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        log.error("Creation person error : " + params.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/person")
    public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody PutParams params) {
        PersonDto updatedPersonDto = personService.updatePerson(params);

        if (updatedPersonDto == null) {
            log.error("Update person error : " + params.toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedPersonDto);
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> deletePerson(@Valid @RequestBody DeleteParams params) {
        if (personService.deletePerson(params)) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        log.error("Delete person error : " + params.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
