package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.firestation.DeleteBody;
import com.safetynet.alerts.dto.firestation.FireStationDto;
import com.safetynet.alerts.dto.firestation.PostBody;
import com.safetynet.alerts.dto.firestation.PutBody;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FireStationService fireStationService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testPostFireStationMapOk() throws Exception {
        PostBody body = new PostBody();
        body.setAddress("1 rue de la paix");
        body.setStation(10);

        when(fireStationService.postFireStation(any())).thenReturn(true);
        mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testPostFireStationMapKo() throws Exception {
        PostBody body = new PostBody();
        body.setAddress("1 rue de la paix");
        body.setStation(10);

        when(fireStationService.postFireStation(any())).thenReturn(false);
        mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateFireStationNumberOk() throws Exception {
        PutBody body = new PutBody();
        body.setAddress("1 rue de la paix");
        body.setStation(10);

        when(fireStationService.updateStationNumber(any())).thenReturn(new FireStationDto());
        mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testUpdateFireStationNumberKo() throws Exception {
        PutBody body = new PutBody();
        body.setAddress("1 rue de la paix");
        body.setStation(10);

        when(fireStationService.updateStationNumber(any())).thenReturn(null);
        mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteFireStationMappingOk() throws Exception {
        DeleteBody body = new DeleteBody();
        body.setAddress("1 rue de la paix");
        body.setStation(10);

        when(fireStationService.deleteFireStationMap(any())).thenReturn(true);
        mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testDeleteFireStationMappingKo() throws Exception {
        DeleteBody body = new DeleteBody();
        body.setAddress("1 rue de la paix");
        body.setStation(10);

        when(fireStationService.deleteFireStationMap(any())).thenReturn(false);
        mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }
}
