package com.safetynet.alerts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonInfoStationDto {
    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private List<String> medications;
    private List<String> allergies;
}
