package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dto.medical.DeleteBody;
import com.safetynet.alerts.dto.medical.MedicalRecordDto;
import com.safetynet.alerts.dto.medical.PostBody;
import com.safetynet.alerts.dto.medical.PutBody;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MedicalRecordService medicalRecordService;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void testPostMedicalRecordOk() throws Exception {
        PostBody body = new PostBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1922, 1, 1));

        when(medicalRecordService.postMedicalRecord(any())).thenReturn(true);
        mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testPostMedicalRecordKo() throws Exception {
        PostBody body = new PostBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1922, 1, 1));

        when(medicalRecordService.postMedicalRecord(any())).thenReturn(false);
        mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }

    @Test
    void testPutMedicalRecordOk() throws Exception {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1922, 1, 1));

        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(new MedicalRecordDto());
        mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testPutMedicalRecordKo() throws Exception {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setBirthdate(LocalDate.of(1922, 1, 1));

        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(null);
        mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteMedicalRecordOk() throws Exception {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        when(medicalRecordService.deleteMedicalRecord(any())).thenReturn(true);
        mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testDeleteMedicalRecordKo() throws Exception {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        when(medicalRecordService.deleteMedicalRecord(any())).thenReturn(false);
        mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }
}
