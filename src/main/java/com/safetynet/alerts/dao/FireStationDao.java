package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationDao {
    List<FireStation> getFireStationByStation(List<Integer> nbStationList);

    List<FireStation> getFireStationByStation(Integer stationNb);

    Optional<FireStation> getFireStationByAddress(String address);

    List<String> getFireStationAddressesByStation(Integer stationNb);

    boolean addFireStation(FireStation fireStation);

    Optional<FireStation> getFireStation(String address, Integer station);

    void updateFireStation(FireStation fireStation);

    boolean deleteFireStation(FireStation fireStation);
}
