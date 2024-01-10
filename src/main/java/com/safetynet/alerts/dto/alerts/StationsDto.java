package com.safetynet.alerts.dto.alerts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StationsDto {
    private String address;
    private List<PersonDto> persons;
}