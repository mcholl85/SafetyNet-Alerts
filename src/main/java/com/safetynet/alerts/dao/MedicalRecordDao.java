package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.Optional;

public interface MedicalRecordDao {
    Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName);

    boolean addMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(MedicalRecord medicalRecord);

    boolean deleteMedicalRecord(MedicalRecord medicalRecord);
}
