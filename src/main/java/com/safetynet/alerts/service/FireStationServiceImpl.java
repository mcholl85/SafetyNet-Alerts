package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dto.firestation.DeleteBody;
import com.safetynet.alerts.dto.firestation.FireStationDto;
import com.safetynet.alerts.dto.firestation.PostBody;
import com.safetynet.alerts.dto.firestation.PutBody;
import com.safetynet.alerts.model.FireStation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class FireStationServiceImpl implements FireStationService {
    private final FireStationDao fireStationDao;

    @Autowired
    public FireStationServiceImpl(FireStationDao fireStationDao) {
        this.fireStationDao = fireStationDao;
    }

    @Override
    public boolean postFireStation(PostBody body) {
        Optional<FireStation> fireStationOptional = fireStationDao.getFireStation(body.getAddress(), body.getStation());

        if (fireStationOptional.isPresent()) {
            log.error("Creation FireStation error : FireStationMap already exists");
            return false;
        }

        return fireStationDao.addFireStation(new FireStation(body.getAddress(), body.getStation()));
    }

    @Override
    public FireStationDto updateStationNumber(PutBody body) {
        Optional<FireStation> optionalFireStation = fireStationDao.getFireStationByAddress(body.getAddress());

        if (optionalFireStation.isEmpty()) {
            log.error("Update FireStation error : FireStationMap does not exist");
            return null;
        }

        FireStation updatedFireStation = optionalFireStation.get();
        updatedFireStation.setStation(body.getStation());

        try {
            fireStationDao.updateFireStation(updatedFireStation);
        } catch (Exception e) {
            log.error("Update FireStation error : " + e.getMessage());
            return null;
        }

        FireStationDto fireStationDto = new FireStationDto();
        fireStationDto.setAddress(updatedFireStation.getAddress());
        fireStationDto.setStation(updatedFireStation.getStation());

        return fireStationDto;
    }

    public boolean deleteFireStationMap(DeleteBody body) {
        Optional<FireStation> optionalFireStation = fireStationDao.getFireStation(body.getAddress(), body.getStation());

        if (optionalFireStation.isEmpty()) {
            log.error("Delete FireStation error : FireStationMap does not exist");
            return false;
        }

        return fireStationDao.deleteFireStation(optionalFireStation.get());
    }
}
