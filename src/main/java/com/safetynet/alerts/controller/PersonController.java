package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.alerts.PersonDto;
import com.safetynet.alerts.dto.person.DeleteBody;
import com.safetynet.alerts.dto.person.PostBody;
import com.safetynet.alerts.dto.person.PutBody;
import com.safetynet.alerts.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<HttpStatus> postPerson(@Valid @RequestBody PostBody body) {
        if (this.personService.postPerson(body)) {
            return ResponseEntity.ok(HttpStatus.CREATED);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/person")
    public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody PutBody body) {
        PersonDto updatedPersonDto = personService.updatePerson(body);

        if (updatedPersonDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedPersonDto);
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> deletePerson(@Valid @RequestBody DeleteBody body) {
        if (personService.deletePerson(body)) {
            return ResponseEntity.ok(HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }
}
