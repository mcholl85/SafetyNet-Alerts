package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.alerts.PersonDto;
import com.safetynet.alerts.dto.person.DeleteBody;
import com.safetynet.alerts.dto.person.PostBody;
import com.safetynet.alerts.dto.person.PutBody;

public interface PersonService {
    boolean postPerson(PostBody body);

    PersonDto updatePerson(PutBody body);

    boolean deletePerson(DeleteBody body);
}
