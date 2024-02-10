package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.medical.DeleteBody;
import com.safetynet.alerts.dto.medical.MedicalRecordDto;
import com.safetynet.alerts.dto.medical.PostBody;
import com.safetynet.alerts.dto.medical.PutBody;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> putMedicalRecord(@RequestBody PutBody body) {
        MedicalRecordDto medicalRecordDto = medicalRecordService.updateMedicalRecord(body);

        if (medicalRecordDto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordDto);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@RequestBody DeleteBody body) {
        if (medicalRecordService.deleteMedicalRecord(body)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.badRequest().build();
    }
}
