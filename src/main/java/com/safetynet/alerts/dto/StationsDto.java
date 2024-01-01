package com.safetynet.alerts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StationsDto {
    private String address;
    @JsonProperty(value = "persons")
    private List<PersonInfoStationDto> personInfoStationDtoList;
}
