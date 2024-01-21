package com.safetynet.alerts.dao.impl;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dto.alerts.DataDto;
import com.safetynet.alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FireStationDaoImpl implements FireStationDao {
    private List<FireStation> fireStationList;

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
    public Optional<FireStation> getFireStationByAddress(String address) {
        return this.fireStationList.stream().filter(f -> f.getAddress().equals(address)).findFirst();
    }

    @Override
    public List<String> getFireStationAddressesByStation(Integer stationNb) {
        return this.getFireStationByStation(stationNb).stream().map(FireStation::getAddress).toList();
    }

    @Override
    public boolean addFireStation(FireStation fireStation) {
        return this.fireStationList.add(fireStation);
    }

    @Override
    public Optional<FireStation> getFireStation(String address, Integer station) {
        return this.fireStationList.stream().filter(fireStation -> fireStation.getAddress().equals(address) && fireStation.getStation().equals(station)).findFirst();
    }

    @Override
    public void updateFireStation(FireStation fireStation) {
        this.fireStationList = this.fireStationList.stream().map(f -> {
            if (f.getAddress().equals(fireStation.getAddress()) && f.getStation().equals(fireStation.getStation()))
                return fireStation;
            return f;
        }).toList();
    }

    @Override
    public boolean deleteFireStation(FireStation fireStation) {
        return this.fireStationList.remove(fireStation);
    }
}
