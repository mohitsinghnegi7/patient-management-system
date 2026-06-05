package com.patientManagementSystem.patientService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientProducerDTO {
    private String id;
    private String name;
    private String email;
    private String messageType;
}
