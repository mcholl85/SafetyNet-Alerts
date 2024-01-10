package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dto.medical.DeleteParams;
import com.safetynet.alerts.dto.medical.MedicalRecordDto;
import com.safetynet.alerts.dto.medical.PostParams;
import com.safetynet.alerts.dto.medical.PutParams;
import com.safetynet.alerts.model.MedicalRecord;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordDao medicalRecordDao;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordDao medicalRecordDao) {
        this.medicalRecordDao = medicalRecordDao;
    }

    @Override
    public boolean postMedicalRecord(PostParams params) {
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordDao.getMedicalRecord(params.getFirstName(), params.getLastName());

        if (medicalRecordOptional.isPresent()) {
            log.error("Creation MedicalRecord error: Record already exists");
            return false;
        }

        MedicalRecord medicalRecordToAdd = new MedicalRecord(params.getFirstName(), params.getLastName(), params.getBirthdate(), params.getMedications(), params.getAllergies());

        return medicalRecordDao.addMedicalRecord(medicalRecordToAdd);
    }

    @Override
    public MedicalRecordDto updateMedicalRecord(PutParams params) {
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordDao.getMedicalRecord(params.getFirstName(), params.getLastName());

        if (medicalRecordOptional.isEmpty()) {
            log.error("Update MedicalRecord Error : Record does not exist");
            return null;
        }

        MedicalRecord updatedMedicalRecord = medicalRecordOptional.get();
        updatedMedicalRecord.setBirthdate(params.getBirthdate());
        updatedMedicalRecord.setMedications(params.getMedications());
        updatedMedicalRecord.setAllergies(params.getAllergies());

        try {
            medicalRecordDao.updateMedicalRecord(updatedMedicalRecord);
        } catch (Exception e) {
            log.error("Update MedicalRecord error : " + e.getMessage());
            return null;
        }

        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setFirstName(updatedMedicalRecord.getFirstName());
        medicalRecordDto.setLastName(updatedMedicalRecord.getLastName());
        medicalRecordDto.setBirthdate(updatedMedicalRecord.getBirthdate());
        medicalRecordDto.setMedications(updatedMedicalRecord.getMedications());
        medicalRecordDto.setAllergies(updatedMedicalRecord.getAllergies());

        return medicalRecordDto;
    }

    @Override
    public boolean deleteMedicalRecord(DeleteParams params) {
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordDao.getMedicalRecord(params.getFirstName(), params.getLastName());

        if (medicalRecordOptional.isEmpty()) {
            log.error("Delete MedicalRecord error : Record does not exist");
            return false;
        }
        MedicalRecord medicalRecordToDelete = medicalRecordOptional.get();

        return medicalRecordDao.deleteMedicalRecord(medicalRecordToDelete);
    }
}
