package com.safetynet.alerts.dto.person;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostParams {
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String address;
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    @NotEmpty
    private String zip;
    @NotNull
    @NotEmpty
    private String phone;
    @NotNull
    @NotEmpty
    private String email;
}
