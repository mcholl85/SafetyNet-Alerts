package com.safetynet.alerts.dao.impl;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao {
    private List<MedicalRecord> medicalRecordList;

    @Autowired
    public MedicalRecordDaoImpl(DataDto data) {
        this.medicalRecordList = data.getMedicalrecords();
    }

    @Override
    public Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName) {
        return this.medicalRecordList.stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)).findFirst();
    }

    @Override
    public boolean addMedicalRecord(MedicalRecord medicalRecord) {
        return this.medicalRecordList.add(medicalRecord);
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecordList = this.medicalRecordList.stream().map(m -> {
            if (m.getFirstName().equals(medicalRecord.getFirstName()) && m.getLastName().equals(medicalRecord.getLastName()))
                return medicalRecord;
            return m;
        }).toList();
    }

    @Override
    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        return this.medicalRecordList.remove(medicalRecord);
    }
}
