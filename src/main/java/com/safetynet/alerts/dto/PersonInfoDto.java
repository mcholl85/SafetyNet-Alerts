package com.safetynet.alerts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonInfoDto {
    private String firstName;
    private String lastName;
    private String address;
    private Integer age;
    private String email;
    private List<String> medications;
    private List<String> allergies;
}
