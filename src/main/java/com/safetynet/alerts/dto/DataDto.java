package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataDto {
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;
}
