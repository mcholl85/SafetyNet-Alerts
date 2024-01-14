package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.medical.DeleteBody;
import com.safetynet.alerts.dto.medical.MedicalRecordDto;
import com.safetynet.alerts.dto.medical.PostBody;
import com.safetynet.alerts.dto.medical.PutBody;
import com.safetynet.alerts.service.MedicalRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> postMedicalRecord(@RequestBody PostBody body) {
        if (medicalRecordService.postMedicalRecord(body)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        log.error("Creation MedicalRecord error : " + body.toString());
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> putMedicalRecord(@RequestBody PutBody body) {
        MedicalRecordDto medicalRecordDto = medicalRecordService.updateMedicalRecord(body);

        if (medicalRecordDto == null) {
            log.error("Update MedicalRecord error : " + body.toString());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordDto);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@RequestBody DeleteBody body) {
        if (medicalRecordService.deleteMedicalRecord(body)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        log.error("Delete Medical error : " + body.toString());
        return ResponseEntity.badRequest().build();
    }
}
