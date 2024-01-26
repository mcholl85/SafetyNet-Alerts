package com.safetynet.alerts.dto.alerts;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhoneListDto {
    private List<String> phoneList;
}
