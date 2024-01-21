package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dto.medical.DeleteBody;
import com.safetynet.alerts.dto.medical.PostBody;
import com.safetynet.alerts.dto.medical.PutBody;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {
    @Mock
    MedicalRecordDao medicalRecordDao;

    @InjectMocks
    MedicalRecordServiceImpl medicalRecordService;

    @Test
    void testPostMedicalRecordOk() {
        PostBody body = new PostBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1920, 1, 1));
        body.setAllergies(Collections.emptyList());
        body.setMedications(Collections.emptyList());

        when(medicalRecordDao.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.empty());
        when(medicalRecordDao.addMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        assertTrue(medicalRecordService.postMedicalRecord(body));
        verify(medicalRecordDao).addMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    void testPostMedicalRecordKo() {
        PostBody body = new PostBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1920, 1, 1));
        body.setAllergies(Collections.emptyList());
        body.setMedications(Collections.emptyList());

        when(medicalRecordDao.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(new MedicalRecord()));

        assertFalse(medicalRecordService.postMedicalRecord(body));
        verify(medicalRecordDao, never()).addMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    void testUpdateMedicalRecordOk() {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1920, 1, 1));
        body.setAllergies(Collections.emptyList());
        body.setMedications(Collections.emptyList());

        when(medicalRecordDao.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(new MedicalRecord()));

        assertNotNull(medicalRecordService.updateMedicalRecord(body));
        verify(medicalRecordDao).updateMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    void testUpdateMedicalRecordDoesNotExist() {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1920, 1, 1));
        body.setAllergies(Collections.emptyList());
        body.setMedications(Collections.emptyList());

        when(medicalRecordDao.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.empty());
        assertNull(medicalRecordService.updateMedicalRecord(body));
        verify(medicalRecordDao, never()).updateMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    void testUpdateMedicalRecordThrowException() {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1920, 1, 1));
        body.setAllergies(Collections.emptyList());
        body.setMedications(Collections.emptyList());

        when(medicalRecordDao.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(new MedicalRecord()));
        doThrow(new RuntimeException("DataBase error")).when(medicalRecordDao).updateMedicalRecord(any(MedicalRecord.class));

        assertNull(medicalRecordService.updateMedicalRecord(body));
    }

    @Test
    void testDeleteMedicalRecordOk() {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        when(medicalRecordDao.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(new MedicalRecord()));
        when(medicalRecordDao.deleteMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        assertTrue(medicalRecordService.deleteMedicalRecord(body));
        verify(medicalRecordDao).deleteMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    void testDeleteMedicalRecordKo() {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        when(medicalRecordDao.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.empty());

        assertFalse(medicalRecordService.deleteMedicalRecord(body));
    }
}
