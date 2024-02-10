package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.firestation.DeleteBody;
import com.safetynet.alerts.dto.firestation.FireStationDto;
import com.safetynet.alerts.dto.firestation.PostBody;
import com.safetynet.alerts.dto.firestation.PutBody;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {
    private final FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping("/firestation")
    public ResponseEntity<HttpStatus> postFireStationMap(@RequestBody PostBody body) {
        if (fireStationService.postFireStation(body)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStationDto> updateFireStationNumber(@RequestBody PutBody body) {
        FireStationDto fireStationDto = fireStationService.updateStationNumber(body);

        if (fireStationDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(fireStationDto);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<HttpStatus> deleteFireStationMapping(@RequestBody DeleteBody body) {
        if (fireStationService.deleteFireStationMap(body)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
