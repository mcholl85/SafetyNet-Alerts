package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.medical.DeleteParams;
import com.safetynet.alerts.dto.medical.MedicalRecordDto;
import com.safetynet.alerts.dto.medical.PostParams;
import com.safetynet.alerts.dto.medical.PutParams;

public interface MedicalRecordService {
    boolean postMedicalRecord(PostParams params);

    MedicalRecordDto updateMedicalRecord(PutParams params);

    boolean deleteMedicalRecord(DeleteParams params);
}
