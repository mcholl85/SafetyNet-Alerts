package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.firestation.DeleteParams;
import com.safetynet.alerts.dto.firestation.FireStationDto;
import com.safetynet.alerts.dto.firestation.PostParams;
import com.safetynet.alerts.dto.firestation.PutParams;
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
    public ResponseEntity<HttpStatus> postFireStationMap(@RequestBody PostParams params) {
        if (fireStationService.postFireStation(params)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        log.error("Creation FireStation error : " + params.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStationDto> updateFireStationNumber(@RequestBody PutParams params) {
        FireStationDto fireStationDto = fireStationService.updateStationNumber(params);

        if (fireStationDto == null) {
            log.error("Update FireStation error : " + params.toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(fireStationDto);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<HttpStatus> deleteFireStationMapping(@RequestBody DeleteParams params) {
        if (fireStationService.deleteFireStationMap(params)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        log.error("Delete FireStation error: " + params.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
