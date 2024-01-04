package com.safetynet.alerts.dao;

import com.safetynet.alerts.dto.DataDto;
import com.safetynet.alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FireStationDaoImpl implements FireStationDao {
    private final List<FireStation> fireStationList;

    @Autowired
    public FireStationDaoImpl(DataDto data) {
        this.fireStationList = data.getFirestations();
    }

    @Override
    public List<FireStation> getFireStationByStation(List<Integer> nbStationList) {
        return this.fireStationList.stream().filter(fireStation -> nbStationList.contains(fireStation.getStation())).toList();
    }

    @Override
    public List<FireStation> getFireStationByStation(Integer stationNb) {
        return this.fireStationList.stream().filter(fireStation -> fireStation.getStation().equals(stationNb)).toList();
    }

    @Override
    public Integer getFireStationByAddress(String address) {
        Optional<FireStation> fireStation = this.fireStationList.stream().filter(f -> f.getAddress().equals(address)).findFirst();
        return fireStation.map(FireStation::getStation).orElse(0);
    }

    @Override
    public List<String> getFireStationAddressesByStation(Integer stationNb) {
        return this.getFireStationByStation(stationNb).stream().map(FireStation::getAddress).toList();
    }
}
