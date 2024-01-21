package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dto.firestation.DeleteBody;
import com.safetynet.alerts.dto.firestation.PostBody;
import com.safetynet.alerts.dto.firestation.PutBody;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.impl.FireStationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {
    @Mock
    FireStationDao fireStationDao;

    @InjectMocks
    FireStationServiceImpl fireStationService;

    @Test
    void testPostFireStationOk() {
        PostBody body = new PostBody();
        body.setStation(10);
        body.setAddress("123 Street");

        when(fireStationDao.getFireStation(anyString(), anyInt())).thenReturn(Optional.empty());
        when(fireStationDao.addFireStation(any(FireStation.class))).thenReturn(true);

        assertTrue(fireStationService.postFireStation(body));
        verify(fireStationDao).addFireStation(any(FireStation.class));
    }

    @Test
    void testPostFireStationDoesNotExist() {
        PostBody body = new PostBody();
        body.setStation(10);
        body.setAddress("123 Street");

        when(fireStationDao.getFireStation(anyString(), anyInt())).thenReturn(Optional.of(new FireStation()));

        assertFalse(fireStationService.postFireStation(body));
        verify(fireStationDao, never()).addFireStation(any(FireStation.class));
    }

    @Test
    void testUpdateFireStationOk() {
        PutBody body = new PutBody();
        body.setStation(10);
        body.setAddress("123 Street");

        when(fireStationDao.getFireStationByAddress(anyString())).thenReturn(Optional.of(new FireStation()));

        assertNotNull(fireStationService.updateStationNumber(body));
        verify(fireStationDao).updateFireStation(any(FireStation.class));
    }

    @Test
    void testUpdateFireStationDoesNotExist() {
        PutBody body = new PutBody();
        body.setStation(10);
        body.setAddress("123 Street");

        when(fireStationDao.getFireStationByAddress(anyString())).thenReturn(Optional.empty());
        assertNull(fireStationService.updateStationNumber(body));
        verify(fireStationDao, never()).updateFireStation(any(FireStation.class));
    }

    @Test
    void testUpdateFireStationThrowException() {
        PutBody body = new PutBody();
        body.setStation(10);
        body.setAddress("123 Street");

        when(fireStationDao.getFireStationByAddress(anyString())).thenReturn(Optional.of(new FireStation()));
        doThrow(new RuntimeException("DataBase error")).when(fireStationDao).updateFireStation(any(FireStation.class));

        assertNull(fireStationService.updateStationNumber(body));
    }

    @Test
    void testDeleteFireStationMapOk() {
        DeleteBody body = new DeleteBody();
        body.setStation(10);
        body.setAddress("123 Street");

        when(fireStationDao.getFireStation(anyString(), anyInt())).thenReturn(Optional.of(new FireStation()));
        when(fireStationDao.deleteFireStation(any(FireStation.class))).thenReturn(true);

        assertTrue(fireStationService.deleteFireStationMap(body));
        verify(fireStationDao).deleteFireStation(any(FireStation.class));
    }

    @Test
    void testDeleteFireStationMapKo() {
        DeleteBody body = new DeleteBody();
        body.setStation(10);
        body.setAddress("123 Street");

        when(fireStationDao.getFireStation(anyString(), anyInt())).thenReturn(Optional.empty());

        assertFalse(fireStationService.deleteFireStationMap(body));
        verify(fireStationDao, never()).deleteFireStation(any(FireStation.class));
    }
}
