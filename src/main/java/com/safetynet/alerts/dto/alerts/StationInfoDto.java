package com.safetynet.alerts.dto.alerts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StationInfoDto {
    private List<PersonDto> persons;
    private Integer adultCount;
    private Integer childrenCount;
}
