package com.safetynet.alerts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StationInfoDto {
    private List<PersonDto> persons;
    private Integer adultCount;
    private Integer childrenCount;
}
