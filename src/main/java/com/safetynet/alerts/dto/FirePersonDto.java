package com.safetynet.alerts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FirePersonDto {
    private Integer station;
    private List<PersonInfoStationDto> persons;
}
