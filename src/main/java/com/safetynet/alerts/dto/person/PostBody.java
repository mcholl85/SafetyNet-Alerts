package com.safetynet.alerts.dto.person;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostBody {
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
