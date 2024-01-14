package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.medical.DeleteBody;
import com.safetynet.alerts.dto.medical.MedicalRecordDto;
import com.safetynet.alerts.dto.medical.PostBody;
import com.safetynet.alerts.dto.medical.PutBody;

public interface MedicalRecordService {
    boolean postMedicalRecord(PostBody body);

    MedicalRecordDto updateMedicalRecord(PutBody body);

    boolean deleteMedicalRecord(DeleteBody body);
}
