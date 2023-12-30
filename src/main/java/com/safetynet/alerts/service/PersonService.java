package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDto;

import java.util.List;

public interface PersonService {
    List<PersonInfoDto> getPersonInfo(String firstName, String lastName);
}
