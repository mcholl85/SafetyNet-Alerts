package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;

public interface MedicalRecordDao {
    MedicalRecord getMedicalRecord(String firstName, String lastName);
}
