package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.firestation.DeleteBody;
import com.safetynet.alerts.dto.firestation.FireStationDto;
import com.safetynet.alerts.dto.firestation.PostBody;
import com.safetynet.alerts.dto.firestation.PutBody;
import com.safetynet.alerts.service.FireStationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
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
        log.error("Creation FireStation error : " + body.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStationDto> updateFireStationNumber(@RequestBody PutBody body) {
        FireStationDto fireStationDto = fireStationService.updateStationNumber(body);

        if (fireStationDto == null) {
            log.error("Update FireStation error : " + body.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(fireStationDto);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<HttpStatus> deleteFireStationMapping(@RequestBody DeleteBody body) {
        if (fireStationService.deleteFireStationMap(body)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        log.error("Delete FireStation error: " + body.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
