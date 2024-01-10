package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.alerts.PersonDto;
import com.safetynet.alerts.dto.person.DeleteParams;
import com.safetynet.alerts.dto.person.PostParams;
import com.safetynet.alerts.dto.person.PutParams;

public interface PersonService {
    boolean postPerson(PostParams params);

    PersonDto updatePerson(PutParams params);

    boolean deletePerson(DeleteParams params);
}
