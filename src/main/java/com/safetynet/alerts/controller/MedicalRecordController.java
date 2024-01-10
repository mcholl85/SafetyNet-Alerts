package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.medical.DeleteParams;
import com.safetynet.alerts.dto.medical.MedicalRecordDto;
import com.safetynet.alerts.dto.medical.PostParams;
import com.safetynet.alerts.dto.medical.PutParams;
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
    public ResponseEntity<HttpStatus> postMedicalRecord(@RequestBody PostParams params) {
        if (medicalRecordService.postMedicalRecord(params)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        log.error("Creation MedicalRecord error : " + params.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> putMedicalRecord(@RequestBody PutParams params) {
        MedicalRecordDto medicalRecordDto = medicalRecordService.updateMedicalRecord(params);

        if (medicalRecordDto == null) {
            log.error("Update MedicalRecord error : " + params.toString());
        }

        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordDto);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@RequestBody DeleteParams params) {
        if (medicalRecordService.deleteMedicalRecord(params)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        log.error("Delete Medical error : " + params.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
