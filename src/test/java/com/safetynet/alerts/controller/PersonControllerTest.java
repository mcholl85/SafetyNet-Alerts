package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.alerts.PersonDto;
import com.safetynet.alerts.dto.person.DeleteBody;
import com.safetynet.alerts.dto.person.PostBody;
import com.safetynet.alerts.dto.person.PutBody;
import com.safetynet.alerts.service.PersonService;
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

@WebMvcTest(PersonController.class)
class PersonControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testPostPersonOk() throws Exception {
        PostBody body = new PostBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setAddress("1 rue de la paix");
        body.setPhone("323232");
        body.setCity("Paris");
        body.setEmail("johndoe@email.com");
        body.setZip("75019");

        when(personService.postPerson(any())).thenReturn(true);
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testPostPersonKo() throws Exception {
        PostBody body = new PostBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setAddress("1 rue de la paix");
        body.setPhone("323232");
        body.setCity("Paris");
        body.setEmail("johndoe@email.com");
        body.setZip("75019");

        when(personService.postPerson(any())).thenReturn(false);
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }

    @Test
    void testPutPersonOk() throws Exception {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setAddress("1 rue de la paix");
        body.setPhone("323232");
        body.setCity("Paris");
        body.setEmail("johndoe@email.com");
        body.setZip("75019");

        when(personService.updatePerson(any())).thenReturn(new PersonDto());
        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testPutPersonKo() throws Exception {
        PutBody body = new PutBody();
        body.setFirstName("John");
        body.setLastName("Doe");
        body.setAddress("1 rue de la paix");
        body.setPhone("323232");
        body.setCity("Paris");
        body.setEmail("johndoe@email.com");
        body.setZip("75019");

        when(personService.updatePerson(any())).thenReturn(null);
        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }

    @Test
    void testDeletePersonOk() throws Exception {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        when(personService.deletePerson(any())).thenReturn(true);
        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void testDeletePersonKo() throws Exception {
        DeleteBody body = new DeleteBody();
        body.setFirstName("John");
        body.setLastName("Doe");

        when(personService.deletePerson(any())).thenReturn(false);
        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body))).andExpect(status().isBadRequest());
    }
}
