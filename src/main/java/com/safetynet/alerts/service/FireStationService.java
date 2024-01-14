package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.firestation.DeleteBody;
import com.safetynet.alerts.dto.firestation.FireStationDto;
import com.safetynet.alerts.dto.firestation.PostBody;
import com.safetynet.alerts.dto.firestation.PutBody;

public interface FireStationService {
    boolean postFireStation(PostBody body);

    FireStationDto updateStationNumber(PutBody body);

    boolean deleteFireStationMap(DeleteBody body);
}
