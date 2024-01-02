package com.safetynet.alerts.dao;

import com.safetynet.alerts.dto.DataDto;
import com.safetynet.alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        FireStation fireStation = this.fireStationList.stream().filter(f -> f.getAddress().equals(address)).findFirst().orElse(null);
        if (fireStation != null) {
            return fireStation.getStation();
        }
        return 0;
    }
}
