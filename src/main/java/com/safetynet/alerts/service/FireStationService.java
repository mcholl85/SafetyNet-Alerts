package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.firestation.DeleteParams;
import com.safetynet.alerts.dto.firestation.FireStationDto;
import com.safetynet.alerts.dto.firestation.PostParams;
import com.safetynet.alerts.dto.firestation.PutParams;

public interface FireStationService {
    boolean postFireStation(PostParams params);

    FireStationDto updateStationNumber(PutParams params);

    boolean deleteFireStationMap(DeleteParams params);
}
