package com.safetynet.alerts.dto.firestation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FireStationDto {
    private String address;
    private Integer station;
}
