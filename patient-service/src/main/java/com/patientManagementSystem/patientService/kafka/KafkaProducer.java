package com.patientManagementSystem.patientService.kafka;

import com.patientManagementSystem.patientService.dto.PatientProducerDTO;
import com.patientManagementSystem.patientService.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PatientProducerDTO>kafkaTemplate;

    public void sendMessage(Patient patient){
        PatientProducerDTO patientProducerDTO = new PatientProducerDTO();
        patientProducerDTO.setId(patient.getId().toString());
        patientProducerDTO.setName(patient.getName());
        patientProducerDTO.setEmail(patient.getEmail());
        patientProducerDTO.setMessageType("PATIENT_CREATED");

        try {
            kafkaTemplate.send("patient", patientProducerDTO);
            log.info("Patient created Message sent successfully : {}", patientProducerDTO);
        }
        catch (Exception e){
            log.error("Error sending PatientCreated message : {}",patientProducerDTO);
        }
    }
}
