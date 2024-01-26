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
public class StationsDto {
    private String address;
    private List<PersonDto> persons;
}
