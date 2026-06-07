package com.patientManagementSystem.analyticsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientProducerDTO {
    private String id;
    private String name;
    private String email;
    private String messageType;
}
