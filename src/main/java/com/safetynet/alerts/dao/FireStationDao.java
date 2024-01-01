package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.FireStation;

import java.util.List;

public interface FireStationDao {
    List<FireStation> getFireStationByStation(List<Integer> nbStationList);

    Integer getFireStationByAddress(String address);
}
