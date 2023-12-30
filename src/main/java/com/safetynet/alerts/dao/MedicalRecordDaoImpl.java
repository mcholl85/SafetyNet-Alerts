package com.safetynet.alerts.dao;

import com.safetynet.alerts.dto.DataDto;
import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao {
    private final List<MedicalRecord> medicalRecordList;

    @Autowired
    public MedicalRecordDaoImpl(DataDto data) {
        this.medicalRecordList = data.getMedicalrecords();
    }

    public List<MedicalRecord> getMedicalRecords(String firstName, String lastName) {
        return this.medicalRecordList.stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)).toList();
    }
}
