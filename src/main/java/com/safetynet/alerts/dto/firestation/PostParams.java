package com.safetynet.alerts.dto.firestation;

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
    private String address;
    @NotNull
    private Integer station;
}
