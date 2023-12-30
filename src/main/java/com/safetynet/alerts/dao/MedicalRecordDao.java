package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordDao {
    public List<MedicalRecord> getMedicalRecords(String firstName, String lastName);
}
